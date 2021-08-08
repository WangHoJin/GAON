<template lang="">
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <div id="img-div"></div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="session">
      <el-row>
        <el-col :span="12">
          <div id="session-header">
            <h1 id="session-title">{{ mySessionId }}</h1>
            <input
              class="btn btn-large btn-danger"
              type="button"
              id="buttonLeaveSession"
              @click="leaveSession"
              value="Leave session"
            />
            <input
              v-if="vOnOff"
              class="btn btn-large btn-danger"
              type="button"
              id="buttonVideoOff"
              @click="videoOnOff()"
              value="video off"
            />
            <input
              v-else
              class="btn btn-large btn-danger"
              type="button"
              id="buttonVideoOn"
              @click="videoOnOff()"
              value="video on"
            />
            <input
              v-if="aOnOff"
              class="btn btn-large btn-danger"
              type="button"
              id="buttonAudioOff"
              @click="audioOnOff()"
              value="audio off"
            />
            <input
              v-else
              class="btn btn-large btn-danger"
              type="button"
              id="buttonAudioOn"
              @click="audioOnOff()"
              value="audio on"
            />
          </div>
          <div id="main-video" class="col-md-6">
            <user-video :stream-manager="mainStreamManager" />
          </div>
          <div id="video-container" class="col-md-6">
            <user-video
              :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)"
            />
            <user-video
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click.native="updateMainVideoStreamManager(sub)"
            />
          </div>
        </el-col>
        <el-col :span="10">
          <input
            v-if="!chatting"
            class="btn btn-large btn-danger"
            type="button"
            id="buttonChatting"
            @click="chattingOnOff()"
            value="chatting on"
          />
          <input
            v-else
            class="btn btn-large btn-danger"
            type="button"
            id="buttonChatting"
            @click="chattingOnOff()"
            value="chatting off"
          />
          <div v-if="chatting">
            <MessageList :msgs="msgs" />
            <MessageForm @sendMsg="sendMsg" />
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./meeting-components/UserVideo.vue";
import MessageForm from "./meeting-components/messageForm";
import MessageList from "./meeting-components/messageList";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://3.36.51.233:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",

  components: {
    UserVideo,
    MessageForm,
    MessageList
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      msgs: [],
      vOnOff: true,
      aOnOff: true,
      size: true,
      chatting: false,
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100)
    };
  },
  created() {
    this.mySessionId = this.$route.params.conferenceId;
    this.myUserName = JSON.parse(sessionStorage.getItem("userInfo")).nickname;
    // console.log("유저정보");
    // console.log(JSON.parse(sessionStorage.getItem("userInfo")).nickname);
    this.joinSession();
  },
  methods: {
    chattingOnOff() {
      this.chatting = !this.chatting;
      // console.log("현재 나");
      // console.log(this.publisher.stream.connection.data);
      // const { clientData } = JSON.parse(this.publisher.stream.connection.data);
      // const nickname = clientData;
      // console.log("접속자");
      // this.subscribers.forEach(sub => {
      //   console.log(sub.stream.connection.data);
      //   // console.log(JSON.parse(sub.stream.connection.data));
      //   const { clientData } = JSON.parse(sub.stream.connection.data);
      //   console.log(nickname);
      //   console.log(clientData);
      //   if (nickname == clientData) alert("같은 사용자가 존재합니다");
      // });
    },
    audioOnOff() {
      console.log("오디오");
      console.log("변경 전" + this.publisher.publishAudio);
      this.publisher.publishAudio(!this.aOnOff);
      this.aOnOff = !this.aOnOff;
      console.log("변경 후" + this.publisher.publishAudio);
    },
    videoOnOff() {
      // console.log("비디오");
      // console.log("변경 전"+this.publisher.publishVideo);
      this.publisher.publishVideo(!this.vOnOff);
      this.vOnOff = !this.vOnOff;
      // console.log("변경 후"+this.publisher.publishVideo);
    },
    sendMsg(msg) {
      // Sender of the message (after 'session.connect')
      this.session
        .signal({
          data: msg, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "my-chat" // The type of message (optional)
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch(error => {
          console.error(error);
        });
    },
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---
      console.log("접속자");
      console.log(this.subscribers);

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // Receiver of the message (usually before calling 'session.connect')

      this.session.on("signal:my-chat", event => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        const tmp = this.msgs.slice();
        tmp.push(event.data);
        this.msgs = tmp;
      });

      // Receiver of all messages (usually before calling 'session.connect')

      this.session.on("signal", event => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then(token => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "320x200", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch(error => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then(sessionId =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET
              }
            }
          )
          .then(response => response.data)
          .then(data => resolve(data.id))
          .catch(error => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET
              }
            }
          )
          .then(response => response.data)
          .then(data => resolve(data.token))
          .catch(error => reject(error.response));
      });
    }
  }
};
</script>
<style scoped>
#video {
  height: 100px;
  width: 100px;
}
</style>
