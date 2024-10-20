import React, { useState } from "react";

const Modal = ({ showModal, setShowModal, user, handleUpdate }) => {
  const [formData, setFormData] = useState(user);

  const handleChange = (e) => {
 
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    handleUpdate(formData);
    setShowModal(false);
  };

  const handleClose = () => {
    setShowModal(false);
  };

  if (!showModal) return null;

  return (
    <div className="fixed inset-0 p-10   flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-gray-800 text-white p-4 rounded-lg shadow-lg max-w-md w-full max-h-[80%] overflow-y-auto">
        <h2 className="text-xl font-semibold mb-4">Update Information</h2>
        <form onSubmit={handleSubmit}>
          <div className="bg-[#7D60F9] font-bold text-white p-3 mb-4 rounded-xl">User Information</div>
          <div className="mb-4">
            <label className="block mb-2">First Name</label>
            <input
              type="text"
              name="first_name"
              value={formData.first_name}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">Last Name</label>
            <input
              type="text"
              name="last_name"
              value={formData.last_name}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">Phone Number:</label>
            <input
              type="text"
              name="phone_number"
              value={formData.phone_number}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">Email:</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
              required
            />
          </div>

          <div className="bg-[#7D60F9] font-bold text-white p-3 mb-4 rounded-xl">Bank Information</div>
          <div className="mb-4">
            <label className="block mb-2">Account Number:</label>
            <input
              type="text"
              name="account_no"
              value={formData.account_no || ""}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">Bank Name:</label>
            <input
              type="text"
              name="bank_name"
              value={formData.bank_name || ""}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
            
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">IFSC Code:</label>
            <input
              type="text"
              name="ifsc_code"
              value={formData.ifsc_code || ""}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
             
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">UPI ID:</label>
            <input
              type="text"
              name="upi_id"
              value={formData.upi_id || ""}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
              
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">Aadhaar Number:</label>
            <input
              type="text"
              name="aadhaar_number"
              value={formData.aadhaar_number || ""}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
            
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2">PAN Number</label>
            <input
              type="text"
              name="pan_card"
              value={formData.pan_card || ""}
              onChange={handleChange}
              className="w-full border bg-gray-800 px-2 py-1 rounded"
            
            />
          </div>

          <div className="flex justify-end">
            <button type="submit" className="bg-[#7D60F9] text-white py-2 px-4 rounded mr-2">
              Save Changes
            </button>
            <button type="button" onClick={handleClose} className="bg-red-500 text-white py-2 px-4 rounded">
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Modal;
