import { type } from "@testing-library/user-event/dist/type";

export const openModal = () => ({
  type: "OPEN_MODAL",
});

export const closeModal = () => ({
  type: "CLOSE_MODAL",
});
