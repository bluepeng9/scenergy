import ScenergyListItem from "../commons/ScenergyList/ScenergyListItem";
import styles from "./ScenergyList.module.css";
import {useEffect, useState} from "react";
import jobPostApi from "../../apis/JobPost/JobPostApi";

const ScenergyList = ({ onOpenModal }) => {
  const [jobPosts, setJobPosts] = useState([]);

  useEffect(() => {
    jobPostApi.getAllJobPost()
      .then((response) => {
        // console.log("게시글 목록", response.data);
        setJobPosts(response.data); // API 호출 결과로 상태 업데이트
      })
      .catch((error) => {
        console.error("API 에러 호출 에러", error);
      });
  }, [refresh]);

  return (
    <div className={styles.ListGlobal}>
      {jobPosts.length > 0 ? (
        jobPosts.map((post) => (
          <ScenergyListItem
            key={post.id}
            jobstate={post.isActive}
            nickname={post.nickname}
            title={post.title}
            content={post.content}
            peopleRecruited={post.peopleRecruited}
            totalCount={post.totalCount}
            expirationDate={post.expirationDate}
            onOpenModal={() => onOpenModal(post)}
          />
        ))
      ) : (
        <div className={styles.NoPosts}>등록된 게시글이 없습니다.</div>
      )}
    </div>
  );
};
export default ScenergyList;
