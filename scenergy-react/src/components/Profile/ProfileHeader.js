// ProfileHeaderApi.js
import styles from "./ProfileHeader.module.css";
import { useEffect, useState } from "react";
import { getUser } from "../../apis/User/UserApi";
import { Link } from "react-router-dom";
import UserUpdateModal from "../User/UserUpdateModal";

const ProfileHeader = ({ onUpdateUser }) => {
  // 각 요소에 해당하는 상태 정의
  const [profileImage, setProfileImage] = useState(null); // 동그란 프로필 이미지
  const [user_nickname, setUser_nickname] = useState(""); //닉네임
  // const [nickname, setNickname] = useState(""); // 닉네임
  const [bio, setBio] = useState(""); // 한줄소개 입력
  const [videoCount, setVideoCount] = useState(0); // 영상 총 개수
  const [followersCount, setFollowersCount] = useState(0); // 팔로워 수
  const [followingCount, setFollowingCount] = useState(0); // 팔로우 수

  const [isEditing, setIsEditing] = useState(false); // 편집 모드 여부
  const [isUserUpdateModalOpen, setIsUserUpdateModalOpen] = useState(false); // 회원수정

  useEffect(() => {
    getUser2();
  }, []); // 빈 배열을 넣어 컴포넌트가 마운트될 때 한 번만 호출되도록 함

  const getUser2 = async () => {
    const user_nickname = getUser().userNickname;
    console.log(user_nickname);
    setUser_nickname(user_nickname);
  };

  // 프로필 이미지 업로드 이벤트 핸들러
  const handleImageUpload = (event) => {
    const selectedImage = event.target.files[0];
    // 선택된 이미지를 이용하여 상태 업데이트
    setProfileImage(selectedImage);
  };

  // 프로필 편집 버튼 클릭 이벤트
  const handleEditProfile = () => {
    // 프로필 편집 로직
    setIsEditing(true);
  };

  const handleSaveProfile = () => {
    // 저장 로직 (서버에 전송 등)
    setIsEditing(false);
  };

  const handleCancelEdit = () => {
    // 편집 취소 로직
    setIsEditing(false);
  };

  // 회원수정 버튼 클릭 이벤트
  const handleUpdateUser = () => {
    // 부모 컴포넌트에서 전달받은 함수 호출
    onUpdateUser();
    // 모달 열기
    setIsUserUpdateModalOpen(true);
  };

  const handleCloseUserUpdateModal = () => {
    // 모달 닫기
    setIsUserUpdateModalOpen(false);
  };

  const handleConfirmUserUpdate = (newPassword, confirmPassword) => {
    // 실제로 회원 수정 로직을 수행하고 모달 닫기
    // 이 부분은 서버에 실제로 수정 요청을 보내는 등의 로직으로 변경 필요
    console.log("비밀번호 업데이트 :", newPassword);
    // 모달 닫기
    setIsUserUpdateModalOpen(false);
  };

  return (
    <div className={styles.profileHeader}>
      <div className={styles.profileInfo}>
        <div className={styles.profileImgUpload}>
          {/* 동그란 프로필 이미지 */}
          <label htmlFor="profileImageInput" className={styles.uploadContainer}>
            {profileImage ? (
              <img
                src={URL.createObjectURL(profileImage)}
                alt="프로필 이미지"
              />
            ) : (
              <div className={styles.clickUpload}>클릭하여 이미지 업로드</div>
            )}
            <input
              type="file"
              id="profileImageInput"
              onChange={handleImageUpload}
              accept="image/*"
            />
          </label>
        </div>

        <div className={styles.profileNickname}>
          {/* 닉네임, 한줄소개 */}
          <h2>{user_nickname}</h2>
          <p>
            {isEditing ? (
              <input value={bio} onChange={(e) => setBio(e.target.value)} />
            ) : (
              bio || "한줄소개를 입력해주세요."
            )}
          </p>
        </div>
      </div>

      <div>
        {/* 영상 총 개수, 팔로워 수, 팔로우 수 */}
        <div className={styles.profileInfoCount}>
          <span>영상: {videoCount}</span>
          <span>팔로워: {followersCount}</span>
          <span>팔로우: {followingCount}</span>
        </div>

        {/* 프로필편집 버튼, 회원탈퇴버튼, 포트폴리오 링크 */}
        <div className={styles.actionButtons}>
          {isEditing ? (
            <>
              <button onClick={handleSaveProfile}>저장</button>
              <button onClick={handleCancelEdit}>취소</button>
            </>
          ) : (
            <button onClick={handleEditProfile}>프로필편집</button>
          )}
          <button onClick={handleUpdateUser}>회원수정</button>
          <a href="/profile/portfolio" className={styles["portfolio-link"]}>
            <button>포트폴리오</button>
          </a>
        </div>
      </div>
      {/*/!* 회원수정 모달 *!/*/}
      {/*{isUserUpdateModalOpen && (*/}
      {/*    <UserUpdateModal*/}
      {/*        onClose={handleCloseUserUpdateModal}*/}
      {/*        onUpdateUser={handleConfirmUserUpdate}*/}
      {/*    />*/}
      {/*)}*/}
    </div>
  );
};

export default ProfileHeader;
