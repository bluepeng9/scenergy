import React, {useEffect, useState} from "react";
import styles from "./UserUpdateModal.module.css";
import UserApi from "../../apis/User/UserApi";

const UserUpdateModal = ({ onClose, onUpdateUser }) => {
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isPasswordMatched, setIsPasswordMatched] = useState(false);
  const [user_id, setUser_id] = useState("");
  const [user_name, setUser_name] = useState("");
  const [user_nickname, setUser_nickname] = useState("");
  const [user_birth, setUser_birth] = useState("");

  useEffect(() => {
    getUser2();
  }, []); // 빈 배열을 넣어 컴포넌트가 마운트될 때 한 번만 호출되도록 함

  const handleUpdateClick = () => {
    onUpdateUser(newPassword, confirmPassword);
    onClose();
  };

  const handlePasswordMatch = () => {
    const isMatched = true;
    setIsPasswordMatched(isMatched);
  };

  const getUser2 = async () => {
    let user = await UserApi.getUser();
    console.log(user)
    const user_id = user.userId;
    setUser_id(user_id);

    const user_name = user.userName;
    setUser_name(user_name);

    const user_nickname = user.userNickname;
    console.log(user_nickname);
    setUser_nickname(user_nickname);

    const user_birth = user.userBirth;
    console.log(user_birth);
    setUser_birth(user_birth);
    // const nickname = getUser().nickname;
    // const birthday = getUser().birthday;
    // 각각의 label에도 적용
    // setName(name);
    // setNickname(nickname);
    // setBirthday(birthday);
  };

  const handleWithdrawal = () => {
    const isConfirmed = window.confirm(
      "회원탈퇴를 하면 지금까지 저장된 모든 데이터가 사라지게 됩니다. 회원탈퇴를 진행하시겠습니까?",
    );

    if (isConfirmed) {
      onClose();
    }
  };

  return (
    <div className={styles["modal-background"]} onClick={onClose}>
      <div
        className={styles["modal-content"]}
        onClick={(e) => e.stopPropagation()}
      >
        <h2>회원수정</h2>
        <label>
          아이디: <span>{user_id}</span>
        </label>
        {/* 나머지 정보도 여기에 적용 */}
        <label>
          이름: <span>{user_name}</span>
        </label>
        <label>
          닉네임: <span>{user_nickname}</span>
        </label>
        <label>
          생년월일: <span>{user_birth}</span>
        </label>
        <label className={styles["passwordInput"]}>
          비밀번호
          <span className={styles["passwordInputGroup"]}>
            <input
              type="password"
              placeholder="비밀번호 입력"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              onBlur={handlePasswordMatch}
            />
            <button
              className={styles["saveButton"]}
              onClick={handleUpdateClick}
            >
              입력
            </button>
          </span>
        </label>
        {isPasswordMatched && (
          <label className={styles["UserDelete"]}>
            <span>회원탈퇴를 원하십니까?</span>
            <button
              className={styles["withdrawButton"]}
              onClick={handleWithdrawal}
            >
              회원 탈퇴
            </button>
          </label>
        )}

        <div className={styles["closeModalDiv"]}>
          <button className={styles["closeModal"]} onClick={handleUpdateClick}>
            저장
          </button>
          <button className={styles["closeModal"]} onClick={onClose}>
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default UserUpdateModal;

// import React, { useState } from "react";
// import styles from "./UserUpdateModal.module.css";
// import api from "../../apis/api";
// import { getUser } from "../../apis/User/UserApi";
// // import api from "../../apis/api";
// // import { api } from "../api";
//
// const UserUpdateModal = ({ onClose, onUpdateUser }) => {
//   const [newPassword, setNewPassword] = useState("");
//   const [confirmPassword, setConfirmPassword] = useState("");
//   const [isPasswordMatched, setIsPasswordMatched] = useState(false);
//
//   // console.log(UserApi);
//   const handleUpdateClick = () => {
//     // 여기에서 실제 회원 수정 로직을 수행하고, 성공 시 onUpdateAccount 호출
//     // 실패하면 에러 메시지를 표시하거나 적절한 처리를 수행
//     onUpdateUser(newPassword, confirmPassword);
//     onClose();
//   };
//
//   const handlePasswordMatch = () => {
//     // 여기에서 현재 로그인한 비밀번호와 입력한 비밀번호가 일치하는지 확인하는 로직을 추가
//     // 예를 들어, 서버로 비밀번호 일치 여부를 체크하고 그 결과를 isPasswordMatched에 저장
//     // 아래는 임시로 일치한다고 가정한 코드
//     const isMatched = true;
//     setIsPasswordMatched(isMatched);
//   };
//
//   const getUser2 = async () => {
//     // const result = await api.get("user/getUser");
//     // console.log(User.getUser(123));
//     console.log(getUser().userId);
//     // return result.userId;
//   };
//
//   const handleWithdrawal = () => {
//     // 사용자에게 경고창을 띄우고 회원탈퇴를 진행할지 여부를 확인하는 로직
//     const isConfirmed = window.confirm(
//       "회원탈퇴를 하면 지금까지 저장된 모든 데이터가 사라지게 됩니다. 회원탈퇴를 진행하시겠습니까?",
//     );
//
//     if (isConfirmed) {
//       // 여기에서 회원탈퇴 로직을 수행하고, 성공 시 추가 처리
//       // 실패하면 에러 메시지 표시
//       // 사용자가 확인(예)을 선택한 경우 모달을 닫음
//       onClose();
//     }
//   };
//
//   getUser2();
//   // connect to User
//
//   return (
//     <div className={styles["modal-background"]} onClick={onClose}>
//       <div
//         className={styles["modal-content"]}
//         onClick={(e) => e.stopPropagation()}
//       >
//         <h2>회원수정</h2>
//         <label>
//           아이디: <span></span>
//         </label>
//         <label>
//           이름: <span></span>
//         </label>
//         <label>
//           닉네임: <span></span>
//         </label>
//         <label>
//           생년월일: <span></span>
//         </label>
//         {/*<label>*/}
//         {/*    새 비밀번호:*/}
//         {/*    <input*/}
//         {/*        type="password"*/}
//         {/*        value={newPassword}*/}
//         {/*        onChange={(e) => setNewPassword(e.target.value)}*/}
//         {/*    />*/}
//         {/*</label>*/}
//         <label className={styles["passwordInput"]}>
//           비밀번호
//           <span className={styles["passwordInputGroup"]}>
//             <input
//               type="password"
//               placeholder="비밀번호 입력"
//               value={confirmPassword}
//               onChange={(e) => setConfirmPassword(e.target.value)}
//               onBlur={handlePasswordMatch} // 입력이 끝나면 비밀번호 일치 여부 확인
//             />
//             <button
//               className={styles["saveButton"]}
//               onClick={handleUpdateClick}
//             >
//               입력
//             </button>
//           </span>
//         </label>
//         {isPasswordMatched && (
//           <label className={styles["UserDelete"]}>
//             <span>회원탈퇴를 원하십니까?</span>
//             <button
//               className={styles["withdrawButton"]}
//               onClick={handleWithdrawal}
//             >
//               회원 탈퇴
//             </button>
//           </label>
//         )}
//
//         <div className={styles["closeModalDiv"]}>
//           <button className={styles["closeModal"]} onClick={handleUpdateClick}>
//             저장
//           </button>
//           <button className={styles["closeModal"]} onClick={onClose}>
//             취소
//           </button>
//         </div>
//       </div>
//     </div>
//   );
// };
//
// export default UserUpdateModal;
