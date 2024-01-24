import {useRef, useState, useEffect, useCallback} from "react";
import {useParams} from "react-router-dom";
import * as StompJs from "@stomp/stompjs";

function ChatConnect() {
    //채팅 목록
    const [chatList, setChatList] = useState([]);
    const [chat, setChat] = useState("");

    //채팅방 식별 id url에서 가져오기
    const {room_id} = useParams();
    const client = useRef({});

    const subscribe = useCallback(() => {
        client.current.subscribe("/sub/chat/" + room_id, (body) => {
            const json_body = JSON.parse(body.body);
            //_chat_list는 subscribe 함수 내에서만 사용되는 지역변수
            setChatList((_chat_list) => [..._chat_list, json_body]);
        });
    }, [room_id]);

    const connect = useCallback(() => {
        client.current = new StompJs.Client({
            brokerURL: "ws://localhost:8080/ws",
            onConnect: () => {
                console.log("yesyes");
                subscribe();
            }
        });
        client.current.activate();
    }, [subscribe]);

    const publish = (chat) => {
        //연결 끊어지면 끝
        if (!client.current.connected) return;

        //test용
        const message = {
            user_id: 1234,
            room_id: 1,
            chat: chat,
        };

        client.current.publish({
            destination: "/pub/chat",
            body: JSON.stringify(message),
        });

        setChat("");
    };

    const disconnect = () => {
        client.current.deactivate();
    };
    const handleChange = (event) => {
        //입력값 state로 변경해주기
        setChat(event.target.value);
    };
    const handleSubmit = (event, chat) => {
        //보내기 누르면 publish되도록
        event.preventDefault();

        publish(chat);
    };

    useEffect(() => {
        connect();

        return () => disconnect();
    }, [connect]);

    return (
        <div>
            <div>{chatList}</div>
            <form onSubmit={(event) => handleSubmit(event, chat)}>
                <div>
                    <input
                        type={"text"}
                        name={"chatInput"}
                        onChange={handleChange}
                        value={chat}
                    />
                </div>
                <input type={"submit"} value={"보내기"}/>
            </form>
        </div>
    );
}

export default ChatConnect;
