import React, { useEffect, useState } from "react";
import axios from "axios";
import OpenViduSession from "openvidu-react";

const VideoConference = ({ mySessionId, myUserName }) => {
  const [token, setToken] = useState(undefined);

  useEffect(() => {
    joinSession();
  }, [mySessionId, myUserName]);

  const joinSession = () => {
    if (mySessionId && myUserName) {
      getToken().then((token) => {
        setToken(token);
      });
    }
  };

  const getToken = () => {
    return createSession(mySessionId)
      .then((sessionId) => createToken(sessionId))
      .catch((Err) => console.error(Err));
  };

  const createSession = (sessionId) => {
    return new Promise((resolve, reject) => {
      var data = JSON.stringify({ customSessionId: sessionId });
      axios
        .post(
          "https://" + window.location.hostname + ":4443/openvidu/api/sessions",
          data,
          {
            headers: {
              Authorization: "Basic " + btoa("OPENVIDUAPP:MY_SECRET"),
              "Content-Type": "application/json",
            },
          },
        )
        .then((response) => {
          console.log("CREATE SESION", response);
          resolve(response.data.id);
        })
        .catch((response) => {
          var error = Object.assign({}, response);
          if (error.response && error.response.status === 409) {
            resolve(sessionId);
          } else {
            console.log(error);
            console.warn(
              "No connection to OpenVidu Server. This may be a certificate error at " +
                "https://" +
                window.location.hostname +
                ":4443",
            );
            if (
              window.confirm(
                'No connection to OpenVidu Server. This may be a certificate error at "' +
                  "https://" +
                  window.location.hostname +
                  ":4443" +
                  '"\n\nClick OK to navigate and accept it. ' +
                  'If no certificate warning is shown, then check that your OpenVidu Server is up and running at "' +
                  "https://" +
                  window.location.hostname +
                  ":4443" +
                  '"',
              )
            ) {
              window.location.assign(
                "https://" +
                  window.location.hostname +
                  ":4443" +
                  "/accept-certificate",
              );
            }
          }
        });
    });
  };

  const createToken = (sessionId) => {
    return new Promise((resolve, reject) => {
      var data = JSON.stringify({});
      axios
        .post(
          "https://" +
            window.location.hostname +
            ":4443/openvidu/api/sessions/" +
            sessionId +
            "/connection",
          data,
          {
            headers: {
              Authorization: "Basic " + btoa("OPENVIDUAPP:MY_SECRET"),
              "Content-Type": "application/json",
            },
          },
        )
        .then((response) => {
          console.log("TOKEN", response);
          resolve(response.data.token);
        })
        .catch((error) => reject(error));
    });
  };

  const handlerJoinSessionEvent = () => {
    console.log("Join session");
  };

  const handlerLeaveSessionEvent = () => {
    console.log("Leave session");
    setToken(undefined);
  };

  const handlerErrorEvent = () => {
    console.log("Leave session");
  };

  return (
    <div>
      <OpenViduSession
        id="opv-session"
        sessionName={mySessionId}
        user={myUserName}
        token={token}
        joinSession={handlerJoinSessionEvent}
        leaveSession={handlerLeaveSessionEvent}
        error={handlerErrorEvent}
      />
    </div>
  );
};

export default VideoConference;
