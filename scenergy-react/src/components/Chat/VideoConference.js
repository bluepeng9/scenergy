import React, {useEffect, useRef, useState} from 'react';
import Peer from 'peerjs';
import styles from "./ChatRoomReal.module.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faVideo} from "@fortawesome/free-solid-svg-icons";
import VideoConferenceApi from "../../apis/VideoConferenceApi";

const VideoConference = ({chatRoomId, userId, connectUserId}) => {
    const [peerId, setPeerId] = useState('');
    const remoteVideoRef = useRef(null);
    const currentUserVideoRef = useRef(null);
    const peerInstance = useRef(null);
    const callRef = useRef(null);
    const [connectToPeerId, setConnectToPeerId] = useState("");

    useEffect(() => {
        const peer = new Peer();

        peer.on('open', (peerId) => {
            setPeerId(peerId);
            VideoConferenceApi.savePeerId(chatRoomId, parseInt(userId), peerId);
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
        })

        peerInstance.current = peer;

        return () => {
            hangUp()
        };
    }, [chatRoomId, userId]);

    const call = (remotePeerId) => {
        var getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

        getUserMedia({video: true, audio: true}, (mediaStream) => {

            currentUserVideoRef.current.srcObject = mediaStream;
            currentUserVideoRef.current.play();

            const call = peerInstance.current.call(remotePeerId, mediaStream)
            call.on('stream', (remoteStream) => {
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
    }

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
            console.error("Error while handling call after getConnectToPeerId:", error);
        }
    }
    const getConnectToPeerId = async () => {
        try {
            const response = await VideoConferenceApi.getPeerId(chatRoomId, connectUserId);
            return response.data.data;
        } catch (error) {
            console.error("Error while fetching connectToPeerId:", error);
            return null;
        }
    }
    const hangUp = () => {
        if (callRef.current) {
            callRef.current.close(); // 연결된 비디오 중지
        }
        // 비디오 스트림 해제
        if (currentUserVideoRef.current && currentUserVideoRef.current.srcObject) {
            currentUserVideoRef.current.srcObject.getTracks().forEach(track => track.stop());
            currentUserVideoRef.current.srcObject = null;
        }
        if (remoteVideoRef.current && remoteVideoRef.current.srcObject) {
            remoteVideoRef.current.srcObject.getTracks().forEach(track => track.stop());
            remoteVideoRef.current.srcObject = null;
        }
    };

    return (
        <div>
            <div className={styles.doRtc} onClick={handleCall}>
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
