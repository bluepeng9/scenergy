import styles from "./ProfileMiddle.module.css";
import { useState } from "react";

const ProfileMiddle = () => {
    const [isEditing, setIsEditing] = useState(false);
    const [education, setEducation] = useState({
        school: "",
        degree: "",
        admissionDate: "",
        graduationDate: "",
    });
    const [career, setCareer] = useState({
        company: "",
        position: "",
        startDate: "",
        endDate: "",
    });
    const [awards, setAwards] = useState({
        competition: "",
        organizer: "",
        awardDate: "",
    });

    const handleEditToggle = () => {
        setIsEditing(!isEditing);
    };

    const handleInputChange = (field, value, category) => {
        switch (category) {
            case "education":
                setEducation((prevEducation) => ({
                    ...prevEducation,
                    [field]: value,
                }));
                break;
            case "career":
                setCareer((prevCareer) => ({
                    ...prevCareer,
                    [field]: value,
                }));
                break;
            case "awards":
                setAwards((prevAwards) => ({
                    ...prevAwards,
                    [field]: value,
                }));
                break;
            default:
                break;
        }
    };

    const handleSaveChanges = async (category) => {
        try {
            let data = {};
            switch (category) {
                case "education":
                    data = education;
                    break;
                case "career":
                    data = career;
                    break;
                case "awards":
                    data = awards;
                    break;
                default:
                    break;
            }

            // 서버에 수정된 정보 전송
            const response = await fetch(`/api/profile/update${category}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                // 서버에서 응답이 성공적으로 온 경우, 화면에 반영
                setIsEditing(false);
            } else {
                // 서버에서 응답이 실패한 경우, 오류 처리
                console.error(`Failed to save ${category} changes`);
            }
        } catch (error) {
            console.error("Error during request:", error);
        }
    };

    return (
        <>
            <div className={styles['editButton']}>
                <h1>기본 정보</h1>
                {isEditing ? (
                    <button onClick={() => handleSaveChanges}>편집 완료</button>
                ) : (
                    <button onClick={handleEditToggle}>기본정보수정</button>
                )}
            </div>
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
                {isEditing ? (
                    <tr>
                        <td><input type="text" value={education.school} onChange={(e) => handleInputChange("school", e.target.value, "education")} /></td>
                        <td><input type="text" value={education.degree} onChange={(e) => handleInputChange("degree", e.target.value, "education")} /></td>
                        <td><input type="text" value={education.admissionDate} onChange={(e) => handleInputChange("admissionDate", e.target.value, "education")} /></td>
                        <td><input type="text" value={education.graduationDate} onChange={(e) => handleInputChange("graduationDate", e.target.value, "education")} /></td>
                    </tr>
                ) : (
                    <tr>
                        <td>{education.school}</td>
                        <td>{education.degree}</td>
                        <td>{education.admissionDate}</td>
                        <td>{education.graduationDate}</td>
                    </tr>
                )}
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
                {isEditing ? (
                    <tr>
                        <td><input type="text" value={career.company} onChange={(e) => handleInputChange("company", e.target.value, "career")} /></td>
                        <td><input type="text" value={career.position} onChange={(e) => handleInputChange("position", e.target.value, "career")} /></td>
                        <td><input type="text" value={career.startDate} onChange={(e) => handleInputChange("startDate", e.target.value, "career")} /></td>
                        <td><input type="text" value={career.endDate} onChange={(e) => handleInputChange("endDate", e.target.value, "career")} /></td>
                    </tr>
                ) : (
                    <tr>
                        <td>{career.company}</td>
                        <td>{career.position}</td>
                        <td>{career.startDate}</td>
                        <td>{career.endDate}</td>
                    </tr>
                )}
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
                {isEditing ? (
                    <tr>
                        <td><input type="text" value={awards.competition} onChange={(e) => handleInputChange("competition", e.target.value, "awards")} /></td>
                        <td><input type="text" value={awards.organizer} onChange={(e) => handleInputChange("organizer", e.target.value, "awards")} /></td>
                        <td><input type="text" value={awards.awardDate} onChange={(e) => handleInputChange("awardDate", e.target.value, "awards")} /></td>
                    </tr>
                ) : (
                    <tr>
                        <td>{awards.competition}</td>
                        <td>{awards.organizer}</td>
                        <td>{awards.awardDate}</td>
                    </tr>
                )}
                </tbody>
            </table>

            <div className={styles['division-line']}></div>
            <h2>기타사항</h2>
        </>
    );
};

export default ProfileMiddle;
