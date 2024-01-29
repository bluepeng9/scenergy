import { closeModal } from "../actions/actions";
import Dialog from "../components/commons/Dialog/Dialog";
import { connect } from "react-redux";
const NoticePage = ({ isModalOpen, closeModal }) => {
  return (
    <>
      {/*모달이 열려있으면 Dialog 렌더링*/}
      {isModalOpen && (
        <Dialog title="알림" onClose={closeModal}>
          {/*나에게 온 알림 list출력하기*/}
          {/*알림이 없을 때 경우 먼저 렌더링 임시로 해놓음*/}
          <p>알림이 없습니다.</p>
        </Dialog>
      )}
    </>
  );
};
const mapStateToProps = (state) => ({
  isModalOpen: state.isModalOpen,
});
export default connect(mapStateToProps, { closeModal })(NoticePage);
