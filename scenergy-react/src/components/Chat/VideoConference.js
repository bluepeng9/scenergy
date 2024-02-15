import React, { useEffect, useRef, useState } from "react";
import Peer from "peerjs";
import styles from "./VideoConference.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMicrophone,
  faMicrophoneSlash,
  faVideo,
  faVideoSlash,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";
import VideoConferenceApi from "../../apis/VideoConferenceApi";

const VideoConference = ({ chatRoomId, userId, connectUserId }) => {
  const [peerId, setPeerId] = useState("");
  const remoteVideoRef = useRef(null);
  const currentUserVideoRef = useRef(null);
  const peerInstance = useRef(null);
  const callRef = useRef(null);
  const [connectToPeerId, setConnectToPeerId] = useState("");
  const [connectRtc, setConnectRtc] = useState(false);
  const [isAudioOn, setIsAudioOn] = useState(true);
  useEffect(() => {
    const peer = new Peer();

    peer.on("open", (peerId) => {
      setPeerId(peerId);
      VideoConferenceApi.savePeerId(chatRoomId, parseInt(userId), peerId);
    });

    peer.on("call", (call) => {
      var getUserMedia =
        navigator.getUserMedia ||
        navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia;

      getUserMedia({ video: true, audio: true }, (mediaStream) => {
        currentUserVideoRef.current.srcObject = mediaStream;
        currentUserVideoRef.current.play();
        call.answer(mediaStream);
        call.on("stream", function (remoteStream) {
          remoteVideoRef.current.srcObject = remoteStream;
          var playPromise = remoteVideoRef.current.play();

          if (playPromise !== undefined) {
            playPromise.then((_) => {}).catch((error) => {});
          }
        });
      });
    });

    peerInstance.current = peer;

    return () => {
      hangUp();
    };
  }, [chatRoomId, userId]);

  const call = (remotePeerId) => {
    var getUserMedia =
      navigator.getUserMedia ||
      navigator.webkitGetUserMedia ||
      navigator.mozGetUserMedia;

    getUserMedia({ video: true, audio: true }, (mediaStream) => {
      currentUserVideoRef.current.srcObject = mediaStream;
      currentUserVideoRef.current.play();

      const call = peerInstance.current.call(remotePeerId, mediaStream);
      call.on("stream", (remoteStream) => {
        remoteVideoRef.current.srcObject = remoteStream;
        var playPromise = remoteVideoRef.current.play();

        if (playPromise !== undefined) {
          playPromise.then((_) => {}).catch((error) => {});
        }
      });
    });
  };

  const handleChangeAudio = () => {
    if (currentUserVideoRef.current && currentUserVideoRef.current.srcObject) {
      const audioTracks =
        currentUserVideoRef.current.srcObject.getAudioTracks();
      if (audioTracks.length > 0) {
        // 오디오 트랙의 enabled 속성을 토글합니다.
        audioTracks[0].enabled = !audioTracks[0].enabled;
        setIsAudioOn(!isAudioOn); // UI 상태 업데이트
      }
    }
  };

  const handleCall = async () => {
    try {
      const connectToPeerId = await getConnectToPeerId();
      setConnectToPeerId(connectToPeerId);
      if (connectToPeerId !== null) {
        call(connectToPeerId);
      } else {
        console.error("Failed to get connectToPeerId.");
      }
    } catch (error) {
      console.error(
        "Error while handling call after getConnectToPeerId:",
        error,
      );
    }
    setConnectRtc(true);
  };
  const getConnectToPeerId = async () => {
    try {
      const response = await VideoConferenceApi.getPeerId(
        chatRoomId,
        connectUserId,
      );
      return response.data.data;
    } catch (error) {
      console.error("Error while fetching connectToPeerId:", error);
      return null;
    }
  };
  const hangUp = () => {
    if (callRef.current) {
      callRef.current.close(); // 연결된 비디오 중지
    }
    // 비디오 스트림 해제
    if (currentUserVideoRef.current && currentUserVideoRef.current.srcObject) {
      currentUserVideoRef.current.srcObject
        .getTracks()
        .forEach((track) => track.stop());
      currentUserVideoRef.current.srcObject = null;
    }
    if (remoteVideoRef.current && remoteVideoRef.current.srcObject) {
      remoteVideoRef.current.srcObject
        .getTracks()
        .forEach((track) => track.stop());
      remoteVideoRef.current.srcObject = null;
    }
    setConnectRtc(false);
  };

  return (
    <div>
      {!connectRtc && (
        <div className={styles.doRtc} onClick={handleCall}>
          <FontAwesomeIcon icon={faVideo} />
        </div>
      )}
      {connectRtc && (
        <div>
          <div className={styles.videoConferenceViewBack}>
            <div className={styles.videoConferenceView}>
              <div className={styles.videoConferenceItemContainer}>
                <div className={styles.videoConferenceMe}>
                  <video ref={currentUserVideoRef} />
                </div>
                <div className={styles.videoConferenceYou}>
                  <video ref={remoteVideoRef} />
                </div>
              </div>
              <div className={styles.videoConferenceBtn}>
                <div
                  className={styles.controlAudio}
                  onClick={handleChangeAudio}
                >
                  <FontAwesomeIcon
                    icon={isAudioOn ? faMicrophone : faMicrophoneSlash}
                  />
                </div>
                <div className={styles.outRtc} onClick={hangUp}>
                  <FontAwesomeIcon icon={faXmark} />
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default VideoConference;
