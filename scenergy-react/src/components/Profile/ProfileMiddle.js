import styles from "./ProfileMiddle.module.css";

const ProfileMiddle = () => {
    return (
        <>
            <h1>기본 정보</h1>
            <div className={styles['division-line']}></div>
            <h2>학력</h2>
            <table>
                <thead>
                <tr>
                    <th>학교</th>
                    <th>학위</th>
                    <th>입학일</th>
                    <th>졸업일</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>학교 이름</td>
                    <td>학위 정보</td>
                    <td>입학일</td>
                    <td>졸업일</td>
                </tr>

                </tbody>
            </table>

            <div className={styles['division-line']}></div>
            <h2>경력</h2>
            <table>
                <thead>
                <tr>
                    <th>회사</th>
                    <th>직무</th>
                    <th>입사일</th>
                    <th>퇴사일</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>회사명</td>
                    <td>직무명</td>
                    <td>입사날짜</td>
                    <td>졸업날짜</td>
                </tr>

                </tbody>
            </table>

            <div className={styles['division-line']}></div>
            <h2>수상이력</h2>
            <table>
                <thead>
                <tr>
                    <th>대회명</th>
                    <th>주최기관</th>
                    <th>수상날짜</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>대회이름</td>
                    <td>주최</td>
                    <td>수상날짜</td>
                </tr>

                </tbody>
            </table>

            <div className={styles['division-line']}></div>
            <h2>기타사항</h2>
        </>
    )
}

export default ProfileMiddle;