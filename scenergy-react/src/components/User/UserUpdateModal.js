import React, { useState } from 'react';
import styles from './UserUpdateModal.module.css';

const UserUpdateModal = ({ onClose, onUpdateUser }) => {
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isPasswordMatched, setIsPasswordMatched] = useState(false);

    const handleUpdateClick = () => {
        // 여기에서 실제 회원 수정 로직을 수행하고, 성공 시 onUpdateAccount 호출
        // 실패하면 에러 메시지를 표시하거나 적절한 처리를 수행
        onUpdateUser(newPassword, confirmPassword);
        onClose();
    };

    const handlePasswordMatch = () => {
        // 여기에서 현재 로그인한 비밀번호와 입력한 비밀번호가 일치하는지 확인하는 로직을 추가
        // 예를 들어, 서버로 비밀번호 일치 여부를 체크하고 그 결과를 isPasswordMatched에 저장
        // 아래는 임시로 일치한다고 가정한 코드
        const isMatched = true;
        setIsPasswordMatched(isMatched);
    };

    const handleWithdrawal = () => {
        // 사용자에게 경고창을 띄우고 회원탈퇴를 진행할지 여부를 확인하는 로직
        const isConfirmed = window.confirm("회원탈퇴를 하면 지금까지 저장된 모든 데이터가 사라지게 됩니다. 회원탈퇴를 진행하시겠습니까?");

        if (isConfirmed) {
            // 여기에서 회원탈퇴 로직을 수행하고, 성공 시 추가 처리
            // 실패하면 에러 메시지 표시

            // 사용자가 확인(예)을 선택한 경우 모달을 닫음
            onClose();
        }
    };

    return (
        <div className={styles['modal-background']} onClick={onClose}>
            <div className={styles['modal-content']} onClick={(e) => e.stopPropagation()}>
                <h2>회원수정</h2>
                <label>
                    아이디: <span></span>
                </label>
                <label>
                    이름: <span></span>
                </label>
                <label>
                    닉네임: <span></span>
                </label>
                <label>
                    생년월일: <span></span>
                </label>
                {/*<label>*/}
                {/*    새 비밀번호:*/}
                {/*    <input*/}
                {/*        type="password"*/}
                {/*        value={newPassword}*/}
                {/*        onChange={(e) => setNewPassword(e.target.value)}*/}
                {/*    />*/}
                {/*</label>*/}
                <label className={styles['passwordInput']}>
                    비밀번호
                    <span className={styles['passwordInputGroup']}>
                        <input
                            type="password"
                            placeholder="비밀번호 입력"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            onBlur={handlePasswordMatch} // 입력이 끝나면 비밀번호 일치 여부 확인
                        />
                        <button className={styles['saveButton']} onClick={handleUpdateClick}>입력</button>
                    </span>
                </label>
                {isPasswordMatched && (
                    <label className={styles['UserDelete']}>
                        <span>회원탈퇴를 원하십니까?</span>
                        <button className={styles['withdrawButton']} onClick={handleWithdrawal}>회원 탈퇴</button>
                    </label>
                )}

                <div className={styles['closeModalDiv']}>
                    <button className={styles['closeModal']} onClick={handleUpdateClick}>저장</button>
                    <button className={styles['closeModal']} onClick={onClose}>취소</button>
                </div>
            </div>
        </div>
    );
};

export default UserUpdateModal;
