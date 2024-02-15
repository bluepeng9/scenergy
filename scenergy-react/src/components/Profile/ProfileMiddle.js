import styles from "./ProfileMiddle.module.css";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import profileMiddleApi from "../../apis/Profile/ProfileMiddleApi";
import ApiUtil from "../../apis/ApiUtil";

const ProfileMiddle = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [educations, setEducations] = useState([
    {
      institution: "",
      degree: "",
      admissionDate: "",
      graduationDate: "",
    },
  ]);

  const [careers, setCareers] = useState([
    {
      company: "",
      position: "",
      expStartDate: "",
      expEndDate: "",
    },
  ]);

  const [awards, setAwards] = useState([
    {
      honorTitle: "",
      organization: "",
      receicedDate: "",
    },
  ]);

  const [etcs, setEtcs] = useState([
    {
      etcTitle: "",
      etcDescription: "",
      etcStartDate: "",
      etcEndDate: "",
    },
  ]);

  const { userId: portfolioUserId } = useParams();
  const [portfolioId, setPortfolioId] = useState(0); // 포트폴리오가 존재하는지 여부를 추적
  const [userIdFromToken, setUserIdFromToken] = useState(null); // 포트폴리오가 존재하는지 여부를 추적

  useEffect(() => {
    const TmpUserIdFromToken = ApiUtil.getUserIdFromToken();
    setUserIdFromToken(TmpUserIdFromToken);
    testReadPort(portfolioUserId);
  }, []);

  const handleEditToggle = () => {
    setIsEditing(!isEditing);
  };

  //TODO: userId, portfolioId 고정값 헤제해야함
  const handleCreatePortfolio = async () => {
    try {
      const data = {
        userId: userIdFromToken,
      };
      const response = await profileMiddleApi.createPortfolio(data);
      console.log("response");
      console.log(response);
      if (response.status === 200) {
        const portfolioId1 = response.data.data.portfolioId;
        await setPortfolioId(portfolioId1);
        testReadPort(portfolioUserId); // 새로 생성된 포트폴리오를 가져오기
      } else {
        console.error("포트폴리오 생성 실패");
      }
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
    }
  };
  const testReadPort = async (userId) => {
    const data = {
      userId: userId,
    };
    try {
      const response = await profileMiddleApi.readPortfolios(data);
      if (response.status === 200) {
        const portfolio = response.data.data.portfolio;
        // 학력 데이터 설정
        const educationData =
          portfolio.educations.length > 0 ? portfolio.educations : [{}];
        setEducations(educationData);

        // 경력 데이터 설정
        const careerData =
          portfolio.experiences.length > 0 ? portfolio.experiences : [{}];
        setCareers(careerData);

        // 수상이력 데이터 설정
        const awardsData =
          portfolio.honors.length > 0 ? portfolio.honors : [{}];
        setAwards(awardsData);

        // 수상이력 데이터 설정
        const etcsData = portfolio.etcs.length > 0 ? portfolio.etcs : [{}];
        setEtcs(etcsData);
        // portfolio 저장
        setPortfolioId(portfolio.id);
      }
    } catch (error) {
      console.log("포트폴리오가 존재하지 않음");
    }
  };
  const testDeletePort = async () => {
    const data = {
      portfolioId: portfolioId,
      userId: userIdFromToken,
    };
    const response = await profileMiddleApi.deleteAllPortfolios(data);
    console.log("testDeletePort");
    console.log(data);

    setPortfolioId(0);
  };

  const handleInputChange = (field, value, category, index) => {
    switch (category) {
      case "education":
        setEducations((prevEducations) => {
          const newEducations = [...prevEducations];
          newEducations[index] = {
            ...newEducations[index],
            [field]: value,
          };
          return newEducations;
        });
        break;
      case "career":
        setCareers((prevCareers) => {
          const newCareers = [...prevCareers];
          newCareers[index] = {
            ...newCareers[index],
            [field]: value,
          };
          return newCareers;
        });
        break;
      case "awards":
        setAwards((prevAwards) => {
          const newAwards = [...prevAwards];
          newAwards[index] = {
            ...newAwards[index],
            [field]: value,
          };
          return newAwards;
        });
        break;
      case "etcs":
        setEtcs((prevEtcs) => {
          const newEtcs = [...prevEtcs];
          newEtcs[index] = {
            ...newEtcs[index],
            [field]: value,
          };
          return newEtcs;
        });
        break;
      default:
        break;
    }
  };

  const handleSaveChanges = async () => {
    try {
      const data = {
        userId: userIdFromToken,
        portfolioId: portfolioId,
        description: "update descr",
        educations: educations,
        experiences: careers,
        honors: awards,
        etcs: etcs,
      };
      const response = await profileMiddleApi.updateAllPortfolios(data);
      if (response.status == 200) {
        // 서버에서 응답이 성공적으로 온 경우, 화면에 반영
        setIsEditing(false);
      } else {
        // 서버에서 응답이 실패한 경우, 오류 처리
        console.error(`Failed to save changes`);
      }
      setIsEditing(!isEditing);
      testReadPort(portfolioUserId);
    } catch (error) {
      console.error("Error during request:", error);
    }
  };

  const handleAddItem = (category) => {
    switch (category) {
      case "education":
        setEducations((prevEducations) => [
          ...prevEducations,
          {
            institution: "",
            degree: "",
            admissionDate: "",
            graduationDate: "",
          },
        ]);
        break;
      case "career":
        setCareers((prevCareers) => [
          ...prevCareers,
          {
            company: "",
            position: "",
            expStartDate: "",
            expEndDate: "",
          },
        ]);
        break;
      case "awards":
        setAwards((prevAwards) => [
          ...prevAwards,
          {
            honorTitle: "",
            organization: "",
            receicedDate: "",
          },
        ]);
        break;
      case "etcs":
        setEtcs((prevEtcs) => [
          ...prevEtcs,
          {
            etcTitle: "",
            etcDescription: "",
            etcStartDate: "",
            etcEndDate: "",
          },
        ]);
        break;
      default:
        break;
    }
  };

  const handleRemoveItem = (category, index) => {
    switch (category) {
      case "education":
        setEducations((prevEducations) => {
          const newEducations = [...prevEducations];
          newEducations.splice(index, 1);
          return newEducations;
        });
        break;
      case "career":
        setCareers((prevCareers) => {
          const newCareers = [...prevCareers];
          newCareers.splice(index, 1);
          return newCareers;
        });
        break;
      case "awards":
        setAwards((prevAwards) => {
          const newAwards = [...prevAwards];
          newAwards.splice(index, 1);
          return newAwards;
        });
        break;
      case "etcs":
        setEtcs((prevEtcs) => {
          const newEtcs = [...prevEtcs];
          newEtcs.splice(index, 1);
          return newEtcs;
        });
        break;
      default:
        break;
    }
  };

  return (
    <>
      <div className={styles["editButton"]}>
        <h1 className={styles.infoTitle}>기본 정보</h1>
        {portfolioUserId === userIdFromToken && (
          <>
            {portfolioId ? (
              // portfolioId가 있을 때
              <>
                {isEditing ? (
                  <button onClick={handleSaveChanges}>편집 완료</button>
                ) : (
                  <button onClick={handleEditToggle}>기본정보수정</button>
                )}
                <button onClick={testDeletePort}>Delete Test</button>
              </>
            ) : (
              // portfolioId가 없을 때
              <button onClick={handleCreatePortfolio}>기본정보 생성</button>
            )}
          </>
        )}
      </div>
      <div className={styles["division-line"]}></div>

      {portfolioId ? (
        <>
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
              {isEditing
                ? educations.map((education, index) => (
                    <tr key={index}>
                      <td>
                        <input
                          type="text"
                          value={education.institution}
                          onChange={(e) =>
                            handleInputChange(
                              "institution",
                              e.target.value,
                              "education",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={education.degree}
                          onChange={(e) =>
                            handleInputChange(
                              "degree",
                              e.target.value,
                              "education",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={education.admissionDate}
                          onChange={(e) =>
                            handleInputChange(
                              "admissionDate",
                              e.target.value,
                              "education",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={education.graduationDate}
                          onChange={(e) =>
                            handleInputChange(
                              "graduationDate",
                              e.target.value,
                              "education",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <button onClick={() => handleAddItem("education")}>
                          학력 추가
                        </button>
                        <button
                          onClick={() => handleRemoveItem("education", index)}
                        >
                          학력 삭제
                        </button>
                      </td>
                    </tr>
                  ))
                : educations.map((education, index) => (
                    <tr key={index}>
                      <td>{education.institution}</td>
                      <td>{education.degree}</td>
                      <td>{education.admissionDate}</td>
                      <td>{education.graduationDate}</td>
                    </tr>
                  ))}
            </tbody>
          </table>

          <div className={styles["division-line"]}></div>
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
              {isEditing
                ? careers.map((career, index) => (
                    <tr key={index}>
                      <td>
                        <input
                          type="text"
                          value={career.company}
                          onChange={(e) =>
                            handleInputChange(
                              "company",
                              e.target.value,
                              "career",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={career.position}
                          onChange={(e) =>
                            handleInputChange(
                              "position",
                              e.target.value,
                              "career",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={career.expStartDate}
                          onChange={(e) =>
                            handleInputChange(
                              "expStartDate",
                              e.target.value,
                              "career",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={career.expEndDate}
                          onChange={(e) =>
                            handleInputChange(
                              "expEndDate",
                              e.target.value,
                              "career",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <button onClick={() => handleAddItem("career")}>
                          경력 추가
                        </button>
                        <button
                          onClick={() => handleRemoveItem("career", index)}
                        >
                          경력 삭제
                        </button>
                      </td>
                    </tr>
                  ))
                : careers.map((career, index) => (
                    <tr key={index}>
                      <td>{career.company}</td>
                      <td>{career.position}</td>
                      <td>{career.expStartDate}</td>
                      <td>{career.expEndDate}</td>
                    </tr>
                  ))}
            </tbody>
          </table>

          <div className={styles["division-line"]}></div>
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
              {isEditing
                ? awards.map((award, index) => (
                    <tr key={index}>
                      <td>
                        <input
                          type="text"
                          value={award.honorTitle}
                          onChange={(e) =>
                            handleInputChange(
                              "honorTitle",
                              e.target.value,
                              "awards",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={award.organization}
                          onChange={(e) =>
                            handleInputChange(
                              "organization",
                              e.target.value,
                              "awards",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={award.receicedDate}
                          onChange={(e) =>
                            handleInputChange(
                              "receicedDate",
                              e.target.value,
                              "awards",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <button onClick={() => handleAddItem("awards")}>
                          수상 추가
                        </button>
                        <button
                          onClick={() => handleRemoveItem("awards", index)}
                        >
                          수상 삭제
                        </button>
                      </td>
                    </tr>
                  ))
                : awards.map((award, index) => (
                    <tr key={index}>
                      <td>{award.honorTitle}</td>
                      <td>{award.organization}</td>
                      <td>{award.receicedDate}</td>
                    </tr>
                  ))}
            </tbody>
          </table>
          <div className={styles["division-line"]}></div>
          <h2>기타사항</h2>
          <table>
            <thead>
              <tr>
                <th>Title</th>
                <th>Detail</th>
                <th>시작일</th>
                <th>종료일</th>
              </tr>
            </thead>
            <tbody>
              {isEditing
                ? etcs.map((etc, index) => (
                    <tr key={index}>
                      <td>
                        <input
                          type="text"
                          value={etc.etcTitle}
                          onChange={(e) =>
                            handleInputChange(
                              "etcTitle",
                              e.target.value,
                              "etcs",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={etc.etcDescription}
                          onChange={(e) =>
                            handleInputChange(
                              "etcDescription",
                              e.target.value,
                              "etcs",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={etc.etcStartDate}
                          onChange={(e) =>
                            handleInputChange(
                              "etcStartDate",
                              e.target.value,
                              "etcs",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          value={etc.etcEndDate}
                          onChange={(e) =>
                            handleInputChange(
                              "etcEndDate",
                              e.target.value,
                              "etcs",
                              index,
                            )
                          }
                        />
                      </td>
                      <td>
                        <button onClick={() => handleAddItem("etcs")}>
                          기타 추가
                        </button>
                        <button onClick={() => handleRemoveItem("etcs", index)}>
                          기타 삭제
                        </button>
                      </td>
                    </tr>
                  ))
                : etcs.map((etc, index) => (
                    <tr key={index}>
                      <td>{etc.etcTitle}</td>
                      <td>{etc.etcDescription}</td>
                      <td>{etc.etcStartDate}</td>
                      <td>{etc.etcEndDate}</td>
                    </tr>
                  ))}
            </tbody>
          </table>
        </>
      ) : null}
    </>
  );
};
export default ProfileMiddle;
