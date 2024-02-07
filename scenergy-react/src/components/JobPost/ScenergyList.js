import ScenergyListItem from "../commons/ScenergyList/ScenergyListItem";
import styles from "./ScenergyList.module.css";
import { getAllJobPost } from "../../apis/ScenergyApi";
import { useState } from "react";
const ScenergyList = () => {
    //나중에 back api랑 연결해서 map때려서 받아오면 된다
    const userId = 2;
  const [jobPosts, setJobPosts] = useState([]);
  //나중에 back api랑 연결해서 map때려서 받아오면 된다
  getAllJobPost()
    .then((response) => {
      console.log("게시글 목록", response.data);
      setJobPosts(response.data);
    })
    .catch((error) => {
      console.error("api 에러 호출 에러", error);
    }, []);

  return (
    <div className={styles.ListGlobal}>
      {jobPosts.map((post) => (
        <ScenergyListItem
          key={post.userDto.id}
          jobstate={post.state}
          nickname={post.userDto.nickname}
          title={post.title}
          content={post.content}
          peopleRecruited={post.peopleRecruited}
          totalCount={post.totalCount}
        />
      ))}
    </div>
  );
};
export default ScenergyList;
