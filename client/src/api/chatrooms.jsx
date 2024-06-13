import axios from "axios";

export const getAllChatrooms = async () => {
  const { data } = await axios.get(`/api/chatrooms/public`);
  return data;
};

export const getUserChatrooms = async (userId) => {
  console.log("[APIgetUserChatrooms] userId: ", userId);
  const { data } = await axios.get(`/api/users/${userId}/public`);
  console.log("[APIgetUserChatrooms] data: ", data);
  return data;
};

export const getAllUsers = async () => {
  const { data } = await axios.get(`/api/users/public`);
  return data;
};

export const getSpecificChatroom = async (roomId) => {
  console.log("[API] roomId: ", roomId);
  const { data } = await axios.get(`/api/chatrooms/${roomId}`);
  console.log("[API] data: ", data);
  return data;
};
