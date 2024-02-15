// ProfilePage.js
import React, { useState } from "react";
import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./ProfilePage.module.css";
import ProfileHeader from "../components/Profile/ProfileHeader";
import ProfileMiddle from "../components/Profile/ProfileMiddle";
import ProfileVideoList from "../components/Profile/ProfileVideoList";
import UserUpdateModal from "../components/User/UserUpdateModal"; /*회원수정*/

function ProfilePage() {
    const [isUserUpdateModalOpen, setIsUserUpdateModalOpen] = useState(false);

    const handleUpdateUser = () => {
        setIsUserUpdateModalOpen(true);
    };

    const handleCloseUserUpdateModal = () => {
        setIsUserUpdateModalOpen(false);
    };

    const handleConfirmUserUpdate = (newPassword, confirmPassword) => {
        // 실제로 회원 수정 로직을 수행하고 모달 닫기
        // 서버에 실제로 수정 요청을 보내는 등의 로직으로 변경 필요
        console.log("비밀번호 업데이트 :", newPassword);
        // 모달 닫기
        setIsUserUpdateModalOpen(false);
    };

    return (
        <>
            <div className={styles['ProfilePage-global']}>
                <div className={styles['navbarBox']}>
                    <Navbar/>
                </div>

                <div className={styles['ProfilePageContent']}>
                    <ProfileHeader onUpdateUser={handleUpdateUser} />
                    <ProfileMiddle />
                    <ProfileVideoList />
                </div>

            </div>

            {/* 회원수정 모달 */}
            {isUserUpdateModalOpen && (
                <UserUpdateModal
                    onClose={handleCloseUserUpdateModal}
                    onUpdateUser={handleConfirmUserUpdate}
                />
            )}
        </>
    );
}

export default ProfilePage;
