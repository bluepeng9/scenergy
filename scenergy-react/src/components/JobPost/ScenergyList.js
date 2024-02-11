import ScenergyListItem from "../commons/ScenergyList/ScenergyListItem";
import styles from "./ScenergyList.module.css";
import { useEffect, useState } from "react";
import jobPostApi from "../../apis/JobPost/JobPostApi";
import ReactPaginate from "react-paginate";

const ScenergyList = ({ onOpenModal, refresh }) => {
  const [jobPosts, setJobPosts] = useState([]);
  const [pageNumber, setPageNumber] = useState(0);

  const postsPerPage = 6; // 한 페이지에 6개
  const pagesVisited = pageNumber * postsPerPage;

  useEffect(() => {
    jobPostApi
      .getAllJobPost()
      .then((response) => {
        console.log("게시글 목록", response.data);
        setJobPosts(response.data); // API 호출 결과로 상태 업데이트
      })
      .catch((error) => {
        console.error("API 에러 호출 에러", error);
      });
  }, [refresh]);

  const displayPosts = jobPosts
    .slice(pagesVisited, pagesVisited + postsPerPage)
    .map((post) => (
      <ScenergyListItem
        key={post.jobPostId}
        jobPostId={post.jobPostId}
        jobstate={post.isActive}
        nickname={post.userDto.nickname}
        title={post.title}
        content={post.content}
        peopleRecruited={post.peopleRecruited}
        totalCount={post.totalCount}
        expirationDate={post.expirationDate}
        onOpenModal={() => onOpenModal(post)}
      />
    ));

  const pageCount = Math.ceil(jobPosts.length / postsPerPage);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  return (
    <div className={styles.ListGlobalContainer}>
      <div className={styles.ListGlobal}>
        <div className={styles.ListContainer}>{displayPosts}</div>
        <ReactPaginate
          className={styles.ListPaginate}
          previousLabel={"이전"}
          nextLabel={"다음"}
          pageCount={pageCount}
          onPageChange={changePage}
          containerClassName={styles.paginationBttns}
          previousLinkClassName={styles.previousBttn}
          nextLinkClassName={styles.nextBttn}
          disabledClassName={styles.paginationDisabled}
          activeClassName={styles.paginationActive}
        />
      </div>
    </div>
  );
};
export default ScenergyList;
