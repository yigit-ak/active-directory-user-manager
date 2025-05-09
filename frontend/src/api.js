import axios from "axios";

const API_BASE = "http://localhost:8080/api/v1";

const axiosInstance = axios.create({
  baseURL: API_BASE,
  withCredentials: true,
});

const handleRequest = async (requestFunction) => {
  try {
    const response = await requestFunction();
    return response.data;
  } catch (error) {
    console.error("API call failed:", error);
    const message = error.response?.data || "Unexpected error occurred.";
    throw new Error(message);
  }
};

export const getAllVendors = () =>
  handleRequest(() => axiosInstance.get("/vendors"));

export const getUserByCn = (cn) =>
  handleRequest(() => axiosInstance.get(`/users/${cn}`));

export const createUser = (userData) =>
  handleRequest(() => axiosInstance.post(`/users`, userData));

export const resetPassword = (cn) =>
  handleRequest(() => axiosInstance.put(`/users/${cn}/reset-password`));

export const lockUser = (cn) =>
  handleRequest(() => axiosInstance.put(`/users/${cn}/lock`));

export const unlockUser = (cn) =>
  handleRequest(() => axiosInstance.put(`/users/${cn}/unlock`));

export const checkAuth = () =>
  handleRequest(() => axiosInstance.get("/auth"));
