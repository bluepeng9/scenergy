// ProfileHeaderApi.js
import styles from "./ProfileHeader.module.css";
import {useEffect, useState} from "react";
// import { getUser } from "../../apis/User/UserApi";
import {useParams} from "react-router-dom";
import FollowApi from "../../apis/FollowApi";
import ApiUtil from "../../apis/ApiUtil";
import ProfileHeaderApi from "../../apis/Profile/ProfileHeaderApi";
import UserApi from "../../apis/User/UserApi";

const ProfileHeader = ({ onUpdateUser }) => {
  // 각 요소에 해당하는 상태 정의
  const [profileImage, setProfileImage] = useState(null); // 동그란 프로필 이미지
  // const [user_nickname, setUser_nickname] = useState(""); //닉네임 (나중에 유저 닉네임 바꾸면 쓰기)
  // const [nickname, setNickname] = useState(""); // 닉네임
  const [bio, setBio] = useState(""); // 한줄소개 입력
  const [videoCount, setVideoCount] = useState(0); // 영상 총 개수
  const [followersCount, setFollowersCount] = useState(0); // 팔로워 수
  const [followingCount, setFollowingCount] = useState(0); // 팔로우 수
  const [nickname, setNickname] = useState("");
  const [isEditing, setIsEditing] = useState(false); // 편집 모드 여부
  const [url, setUrl] = useState("");
  const [isUserUpdateModalOpen, setIsUserUpdateModalOpen] = useState(false); // 회원수정
  const [isFollowingThisUser, setIsFollowingThisUser] = useState(false); // 회원수정
  const { userId } = useParams();
  useEffect(() => {
    const getUserProfile = async () => {
      console.log(userId);
      try {
        const userData = await ProfileHeaderApi.user(userId);
        console.log("=======", userData);
        setFollowingCount(userData.followingCount);
        setFollowersCount(userData.followerCount);
        setVideoCount(userData.videoCount);
        setNickname(userData.nickname);
        setUrl(userData.url);
        setBio(userData.bio);
        // setProfileImage(userData.url);
        console.log("=====UserData======", userData);
      } catch (error) {
        console.error(" 조회 실패", error);
      }

      let followResponse = await FollowApi.getFollow(
        ApiUtil.getUserIdFromToken(),
        userId,
      );
      setIsFollowingThisUser(followResponse);
    };
    getUserProfile();
  }, [userId]); // 빈 배열을 넣어 컴포넌트가 마운트될 때 한 번만 호출되도록 함

  useEffect(() => {
    const uploadProfileImage = async () => {
      if (profileImage) {
        try {
          const uploadedImageUrl = await UserApi.uploadProfileS3(
            userId,
            profileImage,
          );
          console.log("프로필 이미지 업로드 성공:", uploadedImageUrl);
          // 업로드된 이미지 URL을 상태에 반영하여 프로필 이미지 변경
          setUrl(uploadedImageUrl);
          console.log("업로드 된 이미지", url);
        } catch (error) {
          console.error("프로필 이미지 업로드 실패:", error);
        }
      }
    };

    uploadProfileImage();
  }, [profileImage, userId]);
  const updateUserBio = async () => {
    try {
      // userId와 변경된 bio 값을 uploadBio 메서드에 전달하여 호출합니다.
      const response = await UserApi.uploadBio(userId, bio);
      setBio(response.data.data.bio);
      // setBio(response.bio);
      console.log("bio 업로드 성공", bio);
      console.log("setbio", response);
    } catch (error) {
      console.log(userId, bio);
      console.error("bio 업로드 실패", error);
    }
  };

  const getUser2 = async () => {
    // const user_nickname = getUser().nickname;
    // console.log(user_nickname);
    // setUser_nickname(user_nickname);
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
    updateUserBio();
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

  const follow = async () => {
    if (isFollowingThisUser) {
      let response = await FollowApi.unFollowUser(userId);
      setFollowersCount(response.data.data.countFollowUser);
      setIsFollowingThisUser(false);
      return;
    }

    const fromUserId = ApiUtil.getUserIdFromToken();
    let response = await FollowApi.followUser(parseInt(userId));
    setFollowersCount(response.data.data.userFollowerCount);
    setIsFollowingThisUser(true);
    console.log(parseInt(userId) + " " + fromUserId);
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
    <div className={`${styles.profileHeaders} `}>
      <div className={`${styles.profileHeader} `}>
        <div className={styles.profileInfo}>
          <div className={styles.profileImgUpload}>
            {/* 동그란 프로필 이미지 */}
            <label
              htmlFor="profileImageInput"
              className={styles.uploadContainer}
            >
              {url ? (
                <img src={url} alt="프로필 이미지" />
              ) : (
                  <div className={styles.clickUpload}>Click to upload</div>
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
            <h1>{nickname}</h1>
            {/* 영상 총 개수, 팔로워 수, 팔로우 수 */}
            <div className={styles.profileInfoCount}>
              <span>영상: {videoCount}</span>
              <span>팔로워: {followersCount}</span>
              <span>팔로우: {followingCount}</span>
            </div>
            <div>
              {isEditing ? (
                <input value={bio} onChange={(e) => setBio(e.target.value)} />
              ) : (
                  bio || "자유롭게 자기소개를 입력해주세요."
              )}
            </div>
          </div>
        </div>

        <div style={{width: `20%`, display: `flex`, justifyContent: `flex-end`, alignItems: `start`}}>

          {/* 프로필편집 버튼, 회원탈퇴버튼, 포트폴리오 링크 */}
          <div className={styles.actionButtons}>
            {isEditing ? (
              <>
                <button onClick={handleSaveProfile}>저장</button>
                <button onClick={handleCancelEdit}>취소</button>
              </>
            ) : (
                <>
                  {userId === ApiUtil.getUserIdFromToken() ? (
                      <button onClick={handleEditProfile}>편집</button>) : (<></>)}
                </>
            )}
            {userId === ApiUtil.getUserIdFromToken() ? (<button onClick={handleUpdateUser}>회원수정</button>) : (<></>)}
            {userId !== ApiUtil.getUserIdFromToken() ? (
                <button onClick={follow}>{isFollowingThisUser ? "언팔로우" : "팔로우"}</button>) : (<></>)}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfileHeader;
