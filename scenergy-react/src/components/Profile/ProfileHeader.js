// ProfileHeader.js
import styles from "./ProfileHeader.module.css";
import { useState } from "react";
import { Link } from "react-router-dom";

const ProfileHeader = () => {
    // 각 요소에 해당하는 상태 정의
    const [profileImage, setProfileImage] = useState(null); // 동그란 프로필 이미지
    const [nickname, setNickname] = useState(""); // 닉네임
    const [bio, setBio] = useState(""); // 한줄소개 입력
    const [videoCount, setVideoCount] = useState(0); // 영상 총 개수
    const [followersCount, setFollowersCount] = useState(0); // 팔로워 수
    const [followingCount, setFollowingCount] = useState(0); // 팔로우 수

    // 프로필 이미지 업로드 이벤트 핸들러
    const handleImageUpload = (event) => {
        const selectedImage = event.target.files[0];
        // 선택된 이미지를 이용하여 상태 업데이트
        setProfileImage(selectedImage);
    };

    // 프로필 편집 버튼 클릭 이벤트
    const handleEditProfile = () => {
        // 프로필 편집 로직
    };

    // 회원탈퇴 버튼 클릭 이벤트
    const handleDeleteAccount = () => {
        // 회원탈퇴 로직
    };

    return (
        <div className={styles.profileHeader}>
            <div className={styles.profileInfo}>
                <div className={styles.profileImgUpload}>
                {/* 동그란 프로필 이미지 */}
                <label htmlFor="profileImageInput" className={styles.uploadContainer}>
                    {profileImage ? (
                        <img src={URL.createObjectURL(profileImage)} alt="프로필 이미지" />
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
                {/* 닉네임 */}
                <h2>NickName {nickname}</h2>

                {/* 한줄소개 입력 */}
                <p>한줄소개 {bio}</p>
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
                    <button onClick={handleEditProfile}>프로필편집</button>
                    <button onClick={handleDeleteAccount}>회원탈퇴</button>
                    <Link to="/profile/portfolio" className={styles['portfolio-link']}>
                        포트폴리오
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default ProfileHeader;
