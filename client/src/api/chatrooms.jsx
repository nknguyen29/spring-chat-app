import axios from "axios";

export const joinChatroom = async ({ userId, roomId }) => {
  console.log("[APIjoinChatroom] userId: ", userId, "roomId: ", roomId);
  await axios.put(`/api/chatrooms/${roomId}/users/${userId}`);
};

export const leaveChatroom = async ({ userId, roomId }) => {
  console.log("[APIleaveChatroom] userId: ", userId, "roomId: ", roomId);
  await axios.delete(`/api/chatrooms/${roomId}/users/${userId}`);
};

export const getAllChatrooms = async () => {
  const { data } = await axios.get(`/api/chatrooms`);
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
