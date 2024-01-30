import styles from "./ProfileVideoList.module.css"

const ProfileVideoList = () => {

    return (
        <>
            <div className={styles['division-line']}></div>
            <h1>대표 영상</h1>
            <div className={styles['division-line']}></div>
            <h1>영상 목록</h1>
        </>
    )
}

export default ProfileVideoList;