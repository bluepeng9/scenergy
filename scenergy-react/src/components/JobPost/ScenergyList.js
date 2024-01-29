import ScenergyListItem from "../commons/ScenergyList/ScenergyListItem";
import styles from "./ScenergyList.module.css";
const ScenergyList = () => {
    //나중에 back api랑 연결해서 map때려서 받아오면 된다
  return (
    <div className={styles.ListGlobal}>
      <ScenergyListItem
        jobstate="상태"
        nickname="닉네임"
        title="제목"
        content="내용"
      />
      <ScenergyListItem
        jobstate="상태"
        nickname="닉네임"
        title="제목"
        content="내용"
      />
    </div>
  );
};
export default ScenergyList;
