import axios from "axios";

const API_BASE = process.env.REACT_APP_API_BASE;

const handleRequest = async (requestFn) => {
  try {
    const response = await requestFn();
    return response.data;
  } catch (error) {
    console.error("API call failed:", error);
    const message = error.response?.data || "Unexpected error occurred.";
    throw new Error(message);
  }
};

export const getAllVendors = () =>
  handleRequest(() => axios.get(`${API_BASE}/vendors`));

export const getUserByCn = (cn) =>
  handleRequest(() => axios.get(`${API_BASE}/users/${cn}`));

export const createUser = (userData) =>
  handleRequest(() => axios.post(`${API_BASE}/users`, userData));

export const resetPassword = (cn) =>
  handleRequest(() => axios.put(`${API_BASE}/users/${cn}/reset-password`));

export const lockUser = (cn) =>
  handleRequest(() => axios.put(`${API_BASE}/users/${cn}/lock`));

export const unlockUser = (cn) =>
  handleRequest(() => axios.put(`${API_BASE}/users/${cn}/unlock`));
