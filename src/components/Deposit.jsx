import React, { useEffect, useState } from "react";
import useLocalStorage from "../utils/hooks/useLocalStorage";
import { HOST_URL } from "../utils/constant";
import axios from "axios";
import Modal from "./ModalWithDraw";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import LoadingIcon from "./LoadingIcon"; // Import LoadingIcon component
import toast from "react-hot-toast";

import BackButton from "./BackButton";
import ReactPaginate from "react-paginate";

const Deposit = () => {
  const [userId] = useLocalStorage("authToken"); // 1 hour expiry
  const [machineData, setMachineData] = useState([]);
  const [userData, setUserData] = useState([]);
  const [interestData, setInterestData] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false); // State for modal
  const [loading, setLoading] = useState(true); // Loading state
  const user_data = useSelector((store) => store.user.userInfo);
  const navigate = useNavigate();
  const [flag, setFlag] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const itemsPerPage = 10;

  const toggleTable = () => {
    setIsOpen(!isOpen);
  };

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        setLoading(true); // Set loading to true when fetching starts
        const getUrl = `${HOST_URL}/user/getSingleUser/${userId}`;
        const response = await axios.get(getUrl);

        setMachineData(response.data.user_machines);
        setUserData(response.data);
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false); // Set loading to false when fetching is done
      }
    };

    const fetchInterestData = async () => {
      try {
        const getInterestUrl = `${HOST_URL}/user/getSingleUser+InterestEarned/${userId}`;
        const response = await axios.get(getInterestUrl);
        setInterestData(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    if (!userId) {
      navigate("/login");
    } else {
      fetchUserData();
      fetchInterestData();
    }
  }, [flag]);

  const pageCount = Math.ceil(interestData.length / itemsPerPage);
  const currentItems = interestData.slice(
    currentPage * itemsPerPage,
    (currentPage + 1) * itemsPerPage
  );

  const handlePageChange = (selected) => {
    setCurrentPage(selected.selected);
  };

  const handleButtonClicked = () => {
    if (
      user_data.ifsc_code &&
      user_data.bank_name &&
      user_data.account_no &&
      user_data.upi_id
    ) {
      setIsModalOpen(true);
    } else {
      toast.error(
        "Please Enter your Bank Information From your Profile Section"
      );
    }
  };

  const handleWithdraw = async (amount) => {
    const postUrl = `${HOST_URL}/user+withdrawal/save+pending+request`;

    const formData = {
      user_id: userId,
      withdrawal_amount: amount - amount * 0.1, // Cutting 10% from the original amount
      type: "INTEREST",
      is_success: false,
    };

    console.log("formdata", formData);

    try {
      const response = await axios.post(postUrl, formData);

      if (response.data) {
        setFlag((prev) => !prev);
        toast.success(`Withdrawal of ${amount} initiated successfully!`);
      } else {
        toast.error(
          "Error in processing your withdrawal request. Please try again."
        );
      }
    } catch (error) {
      toast.error(
        "An error occurred while processing your withdrawal. Please try again later."
      );
    }

    setIsModalOpen(false);
  };

  return (
    <>
      <div className=" bg-[#161925]  p-6 space-y-8">
        <BackButton />
        {/* Loading Indicator */}
        {loading ? (
          <div className="flex justify-center items-center h-screen">
            <LoadingIcon /> {/* Show loading icon while loading */}
          </div>
        ) : (
          <>
            {/* First Section: Cards in a Row */}
            <div className="grid  grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
              <div className="bg-blue-500 text-white p-6 rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold">Total Deposit</h2>
                <p className="mt-4 text-lg">
                  ₹{userData.total_deposited_amount}
                </p>
              </div>
              <div className="bg-green-500 text-white p-6 rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold">Total Interest Earned</h2>
                <p className="mt-4 text-lg">
                  ₹{userData.total_interest_earned}
                </p>
              </div>
              <div className="bg-yellow-500 text-white p-6 rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold">Available to Withdraw</h2>
                <p className="mt-4 text-lg">
                  ₹{userData.available_to_withdraw}
                </p>
              </div>
            </div>

            <div className="flex justify-center mb-4">
              <button
                className="bg-blue-600 text-white px-4 py-2 rounded"
                onClick={handleButtonClicked}
              >
                Withdraw
              </button>
            </div>

            {/* Second Section: Table */}
            <div className="px-3 bg-gray-800 rounded-lg shadow-lg">
              <div className="flex flex-col md:flex-row mb-5 py-5 justify-between items-center">
                <h1 className="text-2xl text-white font-bold mb-4 md:mb-0">
                  All Interest Earned
                </h1>
                <button
                  onClick={toggleTable}
                  className="bg-blue-500 text-white px-4 py-2 text-xl rounded-lg hover:bg-blue-600 transition"
                >
                  {isOpen ? "Close Table" : "Open Table"}
                </button>
              </div>

              {isOpen && (
                <div className="overflow-x-auto">
                  {/* Wrapper for horizontal scrolling */}
                  <table className="bg-white min-w-full shadow-lg rounded-lg">
                    <thead>
                      <tr className="bg-gray-700 text-gray-300 text-left text-xs md:text-sm">
                        <th className="py-2 md:py-3 px-2 md:px-6 font-semibold">
                          Machine ID
                        </th>
                        <th className="py-2 md:py-3 px-2 md:px-6 font-semibold">
                          Machine Name
                        </th>
                        <th className="py-2 md:py-3 px-2 md:px-6 font-semibold">
                          Interest Amount
                        </th>
                        <th className="py-2 md:py-3 px-2 md:px-6 font-semibold">
                          Date
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      {currentItems.length > 0 ? (
                        currentItems.map((row, index) => (
                          <tr
                            key={index}
                            className={`border-t border-gray-200 ${
                              index % 2 === 0 ? "bg-gray-100" : "bg-white"
                            }`}
                          >
                            <td className="py-2 md:py-3 px-2 md:px-6 text-gray-700 text-xs md:text-sm">
                              {row.machine_id}
                            </td>
                            <td className="py-2 md:py-3 px-2 md:px-6 text-gray-700 text-xs md:text-sm">
                              {row.machine_name}
                            </td>
                            <td className="py-2 md:py-3 px-2 md:px-6 text-gray-700 text-xs md:text-sm">
                              ₹{row.interest_amount}
                            </td>
                            <td className="py-2 md:py-3 px-2 md:px-6 text-gray-700 text-xs md:text-sm">
                              {new Date(row.date).toLocaleDateString("en-IN", {
                                year: "numeric",
                                month: "short",
                                day: "numeric",
                              })}
                            </td>
                          </tr>
                        ))
                      ) : (
                        <tr>
                          <td
                            colSpan="4"
                            className="py-4 text-center text-gray-500 text-sm"
                          >
                            No data available
                          </td>
                        </tr>
                      )}
                    </tbody>
                  </table>

                  {/* Pagination Logic */}
                  {interestData.length > itemsPerPage && (
                    <ReactPaginate
                      previousLabel={"←"}
                      nextLabel={"→"}
                      breakLabel={"..."}
                      pageCount={pageCount}
                      pageRangeDisplayed={5}
                      onPageChange={handlePageChange}
                      containerClassName={"flex justify-center mt-6"}
                      activeClassName={"bg-green-600 text-white"} // Active class for the current page
                      pageClassName={({ selected }) =>
                        `mx-1 ${
                          selected
                            ? "bg-green-600 text-white"
                            : "bg-gray-700 hover:bg-gray-600 text-white"
                        }`
                      }
                      previousClassName={"mx-1"}
                      nextClassName={"mx-1"}
                      className="pagination flex justify-center items-center space-x-2"
                      pageLinkClassName="px-4 py-2 rounded transition"
                    />
                  )}
                </div>
              )}
            </div>

            {/* Third Section: Dynamic Cards */}
            <h1 className="text-2xl font-semibold mb-6 text-white">My Mines</h1>
            <div className="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-4 gap-6">
              {machineData.map((machine) => (
                <div
                  key={machine.machine.machine_id}
                  className="bg-gray-800 text-white p-4 rounded-lg shadow-lg transition-transform transform hover:scale-105 hover:shadow-xl duration-300"
                >
                  <img
                    src={machine.machine.url} // Correcting the image property name
                    alt={machine.machine.machine_name}
                    loading="lazy"
                    className="  w-full h-40 object-contain rounded-t-lg"
                  />
                  <div className="p-4">
                    <h3 className="text-xl font-semibold mt-2">
                      {machine.machine.machine_name}
                    </h3>
                    <p className="mt-2 text-gray-300">
                      Price:{" "}
                      <span className="text-white">
                        ₹{machine.machine.price}
                      </span>
                    </p>
                    <p className="mt-2 text-gray-300">
                      Valid Days:{" "}
                      <span className="text-white">
                        {machine.machine.valid_days} Days
                      </span>
                    </p>
                    <p className="mt-2 text-gray-300">
                      Start Date:{" "}
                      <span className="text-white">
                        {machine.machine.start_date}
                      </span>
                    </p>
                    <p className="mt-2 text-gray-300">
                      End Date:{" "}
                      <span className="text-white">
                        {machine.machine.end_date}
                      </span>
                    </p>
                    <p className="mt-2 text-gray-300">
                      Status:{" "}
                      <span
                        className={` ${
                          new Date(machine.machine.end_date) > new Date()
                            ? "text-green-500"
                            : "text-red-500"
                        }`}
                      >
                        {new Date(machine.machine.end_date) > new Date()
                          ? "Active"
                          : "Expired"}
                      </span>
                    </p>
                  </div>
                </div>
              ))}
            </div>

            <Modal
              isOpen={isModalOpen}
              onClose={() => setIsModalOpen(false)}
              onWithdraw={handleWithdraw}
              initialAmount={userData.available_to_withdraw}
            />
          </>
        )}
      </div>
    </>
  );
};

export default Deposit;
