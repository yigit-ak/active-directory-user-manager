import axios from "axios";

const API_BASE = process.env.REACT_APP_API_BASE;

export const getAllVendors = async () => {
  const response = await axios.get(`${API_BASE}/vendors`);
  return response.data;
};

export const getUserByCn = async (cn) => {
  const response = await axios.get(`${API_BASE}/users/${cn}`);
  return response.data;
};

export const createUser = async (userData) => {
  const response = await axios.post(`${API_BASE}/users`, userData);
  return response.data;
};

export const resetPassword = async (cn) => {
  const response = await axios.put(`${API_BASE}/users/${cn}/reset-password`);
  return response.data;
};

export const lockUser = async (cn) => {
  const response = await axios.put(`${API_BASE}/users/${cn}/lock`);
  return response.data;
};

export const unlockUser = async (cn) => {
  const response = await axios.put(`${API_BASE}/users/${cn}/unlock`);
  return response.data;
};
