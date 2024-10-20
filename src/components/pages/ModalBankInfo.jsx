import React from 'react';
import { useSelector } from 'react-redux';

const ModalBankInfo = ({ showModal, setShowModal, data, handleApprove }) => {
  

    const user_data = useSelector((store) => store.user.userInfo);
    
    // Close the modal
    const onClose = () => {
        setShowModal(false);
    };

    // Conditional rendering: only show modal if `showModal` is true
    if (!showModal) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-gray-800 text-white w-full max-w-md mx-auto p-8 rounded-lg shadow-lg transition-transform transform duration-300 scale-100 hover:scale-105">
                <h2 className="text-2xl font-semibold mb-6 text-center">Bank Information</h2>
                
                {/* Bank Information */}
                <div className="mb-6">
                    <p className="mb-2"><strong>Account Holder:</strong> {user_data.first_name} {user_data.last_name}</p>
                    <p className="mb-2"><strong>Bank Name:</strong> {user_data?.bank_name || "Not Provided"}</p>
                    <p className="mb-2"><strong>Account Number:</strong> {user_data?.account_no || "Not Provided"}</p>
                    <p className="mb-2"><strong>IFSC Code:</strong> {user_data?.ifsc_code || "Not Provided"}</p>
                    <p className="mb-2"><strong>UPI ID:</strong> {user_data?.upi_id || "Not Provided"}</p>
                    <p className="mb-2"><strong>Withdrawal Amount:</strong> ${data?.withdrawal_amount || "Not Provided"}</p>
                </div>

                {/* Buttons */}
                <div className="flex justify-between">
                    <button
                        onClick={onClose}
                        className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition"
                    >
                        Cancel
                    </button>
                    <button
                        onClick={() => {
                            handleApprove(data); // Call handleApprove with the data
                            onClose(); // Close modal after approval
                        }}
                        className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600 transition"
                    >
                        Approve
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ModalBankInfo;
