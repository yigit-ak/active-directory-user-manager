const API_BASE_URL = "http://localhost:8080"; // TODO: Change to .env

import axios from "axios";

export const fetchAllVendors = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/vendors`);
        return response.data.map(vendor => VendorResponseDto.fromJson(vendor));
    } catch (error) {
        console.error("Error fetching:", error);
    }
};

export const fetchUser = async (commonName) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/users/${commonName}`);
        return UserResponseDto.fromJson(response.data);
    } catch (error) {
        console.error("Error fetching:", error);
    }
};

export const createUser = async (userCreateDto) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/api/v1/users`, userCreateDto.toJson());
        return response.data;
    } catch (error) {
        console.error("Error fetching:", error);
    }
}

export const resetUserPassword = async (userCommonName) => {
    try {
        const response = await axios.put(`${API_BASE_URL}/api/v1/users/${userCommonName}`);
        return response.status;
    } catch (error) {
        console.error("Error fetching:", error);
    }
}

export const enableUserAccount = async (userCommonName) => {
    try {
        const response = await axios.put(`${API_BASE_URL}/api/v1/users/${userCommonName}`);
        return response.status;
    } catch (error) {
        console.error("Error fetching:", error);
    }
}

export const disableUserAccount = async (userCommonName) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/v1/users/${commonName}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching:", error);
    }
}
