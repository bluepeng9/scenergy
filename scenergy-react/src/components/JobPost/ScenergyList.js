import ScenergyListItem from "../commons/ScenergyList/ScenergyListItem";
import styles from "./ScenergyList.module.css";
import {useEffect, useState} from "react";
import jobPostApi from "../../apis/JobPost/JobPostApi";

const ScenergyList = ({ onOpenModal }) => {
  const [jobPosts, setJobPosts] = useState([]);
  //나중에 back api랑 연결해서 map때려서 받아오면 된다
  useEffect(() => {
    jobPostApi.getAllJobPost()
      .then((response) => {
        console.log("게시글 목록", response);
        setJobPosts(response); // API 호출 결과로 상태 업데이트
      })
      .catch((error) => {
        console.error("API 에러 호출 에러", error);
      });
  }, []);

  return (
    <div className={styles.ListGlobal}>
      {jobPosts.length > 0 ? (
        jobPosts.map((post) => (
          <ScenergyListItem
            key={post.userDto.id}
            onOpenModal={onOpenModal}
            jobstate={post.isActive}
            nickname={post.userDto.nickname}
            title={post.title}
            content={post.content}
            peopleRecruited={post.peopleRecruited}
            totalCount={post.totalCount}
            expirtaionDate={post.expirationDate}
          />
        ))
      ) : (
        <div className={styles.NoPosts}>등록된 게시글이 없습니다.</div>
      )}
    </div>
  );
};
export default ScenergyList;
