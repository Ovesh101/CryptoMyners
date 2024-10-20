import React from "react";
import { useNavigate } from "react-router-dom";

const Transaction = () => {
  const navigate = useNavigate();

  return (
    <div className="container mx-auto p-4">
      {/* Container for cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Deposit Card */}
        <div
          onClick={() => navigate("/deposit")}
          className="bg-gradient-to-r from-purple-500 to-blue-500 shadow-lg rounded-lg p-6 flex flex-col items-center justify-center cursor-pointer transform transition-transform duration-300 hover:scale-105"
        >
          <h3 className="text-xl text-white font-semibold mb-4">Deposit Section </h3>
        </div>

        {/* Withdrawal Card */}
        <div
          onClick={() => navigate("/withdrawal")}
          className="bg-gradient-to-r from-green-500 to-teal-500 shadow-lg rounded-lg p-6 flex flex-col items-center justify-center cursor-pointer transform transition-transform duration-300 hover:scale-105"
        >
          <h3 className="text-xl text-white font-semibold mb-4">Withdrawal Section </h3>
        </div>
      </div>
    </div>
  );
};

export default Transaction;
