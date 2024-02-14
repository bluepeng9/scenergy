import React, {useEffect, useRef, useState} from 'react';
import Peer from 'peerjs';
import styles from "./ChatRoomReal.module.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faVideo} from "@fortawesome/free-solid-svg-icons";


const VideoConference = ({chatRoomId, chatRoomUsers, chatRoomUsersSeq, userId, connectUserId}) => {
    const [peerId, setPeerId] = useState('');
    const [remotePeerIdValue, setRemotePeerIdValue] = useState('');
    const remoteVideoRef = useRef(null);
    const currentUserVideoRef = useRef(null);
    const peerInstance = useRef(null);

    useEffect(() => {
        console.log(connectUserId)
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
        })

        peerInstance.current = peer;
    }, [connectUserId])

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

    return (
        <div>
            <div className={styles.doRtc} onClick={() => call("" + chatRoomId + connectUserId + "")}>
                <FontAwesomeIcon icon={faVideo}/>
            </div>
            <div>
                <video ref={currentUserVideoRef}/>
            </div>
            <div>
                <video ref={remoteVideoRef}/>
            </div>
        </div>
    );
}

export default VideoConference;