import React, {useEffect, useRef, useState} from 'react';
import Peer from 'peerjs';
import styles from "./ChatRoomReal.module.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faVideo} from "@fortawesome/free-solid-svg-icons";

const VideoConference = ({chatRoomId, userId, connectUserId}) => {
    const [peerId, setPeerId] = useState('');
    const remoteVideoRef = useRef(null);
    const currentUserVideoRef = useRef(null);
    const peerInstance = useRef(null);
    const callRef = useRef(null);

    useEffect(() => {
        const peer = new Peer("" + chatRoomId + userId + "");

        peer.on('open', (peerId) => {
            setPeerId(peerId)
        });

        peer.on('call', (call) => {
            var getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

            getUserMedia({video: true, audio: true}, (mediaStream) => {
                currentUserVideoRef.current.srcObject = mediaStream;
                currentUserVideoRef.current.play();
                call.answer(mediaStream)
                call.on('stream', function (remoteStream) {
                    remoteVideoRef.current.srcObject = remoteStream
                    var playPromise = remoteVideoRef.current.play();

                    if (playPromise !== undefined) {
                        playPromise.then(_ => {
                        })
                            .catch(error => {
                            });
                    }
                });
            });
            callRef.current = call; // call 객체 저장
        })

        peerInstance.current = peer;

        return () => {
            hangUp()
        };

    }, [chatRoomId, userId, connectUserId]);

    const call = async (remotePeerId) => {
        var getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

        try {
            const mediaStream = await new Promise((resolve, reject) => {
                getUserMedia({video: true, audio: true}, resolve, reject);
            });

            currentUserVideoRef.current.srcObject = mediaStream;
            currentUserVideoRef.current.play();

            const call = peerInstance.current.call(remotePeerId, mediaStream);

            call.on('stream', (remoteStream) => {
                remoteVideoRef.current.srcObject = remoteStream;
                var playPromise = remoteVideoRef.current.play();

                if (playPromise !== undefined) {
                    playPromise.then(_ => {
                    })
                        .catch(error => {
                        });
                }
            });
            callRef.current = call; // call 객체 저장
        } catch (error) {
            console.error('Error accessing media devices:', error);
        }
    };

    const hangUp = () => {
        if (callRef.current) {
            callRef.current.close(); // 연결된 비디오 중지
        }
        // 비디오 스트림 초기화
        currentUserVideoRef.current.srcObject = null;
        remoteVideoRef.current.srcObject = null;
    };

    return (
        <div>
            <div className={styles.doRtc} onClick={() => call("" + chatRoomId + connectUserId + "")}>
                <FontAwesomeIcon icon={faVideo}/>
            </div>
            <div className={styles.doRtc} onClick={hangUp}>
                <FontAwesomeIcon icon={faVideo}/> 종료
            </div>
            <div>
                <video ref={currentUserVideoRef}/>
            </div>
            <div>
                <video ref={remoteVideoRef}/>
            </div>
        </div>
    );
};

export default VideoConference;
