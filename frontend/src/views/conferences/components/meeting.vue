<template lang="">
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <!-- <div id="img-div"></div> -->
      <!-- <div id="join-dialog" class="jumbotron vertical-center"> -->
      <!-- <h1>Join a video session</h1> -->
      <!-- <div class="form-group">
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
        </div> -->
      <!-- </div> -->
    </div>

    <div id="session" v-if="session">
      <el-row>
        <!-- <el-button type="" @click="roll-book-check=true;">출석체크하세요</el-button> -->
        <!-- <RollBookCheck /> -->

        <el-col :span="12">
          <el-button type="" @click="toggleScreanshare()"
            >화면공유버튼</el-button
          >
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
          <!-- <div id="main-video" class="col-md-6">
            <user-video :stream-manager="mainStreamManager" />
          </div> -->
          <div id="video-container" class="col-md-6">
            <user-video
              :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)"
            />
            <!-- 유저 화상회의 목록 출력 START -->
            <div id="userv" v-for="sub in subscribers">
              <user-video
                :key="sub.stream.connection.connectionId"
                :stream-manager="sub"
                @click.native="updateMainVideoStreamManager(sub)"
              />
              <img
                style="cursor:pointer"
                :src="require(`@/common/img/alram.png`)"
                id="alertbtn"
                @click="sendAlert(sub)"
                value="알람보내기"
              />
            </div>
            <!-- 화상회의 출력 END -->
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
          <input
            v-if="!connectionUser"
            class="btn btn-large btn-danger"
            type="button"
            id="buttonConnectionUser"
            @click="connectionUserOnOff()"
            value="connectionUser on"
          />
          <input
            v-else
            class="btn btn-large btn-danger"
            type="button"
            id="buttonConnectionUser"
            @click="connectionUserOnOff()"
            value="connectionUser off"
          />
          <div v-if="chatting">
            <MessageList :msgs="msgs" />
            <MessageForm @sendMsg="sendMsg" />
          </div>
          <div v-if="connectionUser">
            <div v-if="publisher">
              <el-row>
                <ConnetionUserList
                  :publisher="publisher"
                  :subscribers="subscribers"
                  @leaveSession="leaveSession"
                />
              </el-row>
            </div>
          </div>
          <el-button type="" @click="RollBookCheck = true"
            >출석체크하세요</el-button
          >
          <div>
            <RollBookCheck :publisher="publisher" :subscribers="subscribers" />
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import alramimg from "../../../common/mp3/alarm.mp3";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./meeting-components/UserVideo.vue";
import MessageForm from "./meeting-components/messageForm";
import MessageList from "./meeting-components/messageList";
import ConnetionUserList from "./meeting-components/ConnetionUserList";
import alarm from "../../../common/mp3/alarm.mp3";
import { h } from "vue";
import RollBookCheck from "./meeting-components/roll-book-check.vue";
axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://i5b101.p.ssafy.io:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",

  components: {
    UserVideo,
    MessageForm,
    MessageList,
    ConnetionUserList,
    RollBookCheck
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      tempPublisher: undefined,
      subscribers: [],
      msgs: [],
      vOnOff: true,
      aOnOff: true,
      size: true,
      chatting: false,
      connectionUser: false,
      rollBookCheck: false,
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      myUserId: "",
      tg: false
    };
  },
  async created() {
    let roomInfo = await this.$store.dispatch(
      "getRoomById",
      this.$route.params.conferenceId
    );
    this.mySessionId = roomInfo.code;
    this.myUserName = JSON.parse(sessionStorage.getItem("userInfo")).nickname;
    this.myUserId = JSON.parse(sessionStorage.getItem("userInfo")).id;
    this.joinSession();
  },
  methods: {
    sendAlert(sub) {
      console.log(sub.stream.connection);
      this.session
        .signal({
          data: "알람울리기", // Any string (optional)
          to: [sub.stream.connection], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "my-alrarm" // The type of message (optional)
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch(error => {
          console.error(error);
        });
    },
    rollBookCheckOnOff() {
      this.rollBookCheck = !this.rollBookCheck;
    },
    toggleScreanshare() {
      if (!this.tg) {
        this.tg = true;
        var newPublisher = this.OV.initPublisher("user-video", {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: "screen",
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: "320x200", // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
          mirror: false // Whether to mirror your local video or not
        });

        newPublisher.once("accessAllowed", event => {
          newPublisher.stream
            .getMediaStream()
            .getVideoTracks()[0]
            .addEventListener("ended", () => {
              this.toggleScreanshare();
            });
        });

        newPublisher.once("accessAllowed", () => {
          try {
            console.log("크기조정실행됐냐????????????");
            newPublisher.stream
              .getMediaStream()
              .getVideoTracks()[0]
              .applyConstraints({
                width: 1000,
                height: 1000
              });
          } catch (error) {
            console.error("Error applying constraints: ", error);
          }
        });

        this.session.unpublish(this.publisher);
        this.tempPublisher = this.publisher;
        this.publisher = newPublisher;
        this.mainStreamManager = this.publisher;
        this.session.publish(this.publisher);
      } else {
        this.tg = false;
        this.session.unpublish(this.publisher);
        this.publisher = this.tempPublisher;
        this.session.publish(this.publisher);
      }
    },
    connectionUserOnOff() {
      this.connectionUser = !this.connectionUser;
    },
    chattingOnOff() {
      this.chatting = !this.chatting;
    },
    audioOnOff() {
      this.publisher.publishAudio(!this.aOnOff);
      this.aOnOff = !this.aOnOff;
    },
    videoOnOff() {
      this.publisher.publishVideo(!this.vOnOff);
      this.vOnOff = !this.vOnOff;
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
      this.session.on("signal:my-alrarm", event => {
        console.log(event.date + " " + event.from + " " + event.type);
        this.$notify({
          title: "졸지마세요시발아ㅋㅋㅋㅋ",
          message: h(
            "i",
            { style: "color: teal" },
            "일어나~!~!~!~!~!~!~!~!~!~!~!~!~!!"
          )
        });
        var audio = new Audio(alarm);
        audio.play();
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
          .connect(token, {
            clientData: this.myUserName,
            idData: this.myUserId
          })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher("user-video", {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "320x200", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false // Whether to mirror your local video or not
            });

            console.log("this publisher");
            console.log(publisher);
            // console.log(publisher.videoSource("screen"));
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---
            this.session.publish(this.publisher);
            console.log("this publisher2");
            console.log(this.publisher);

            console.log(this.session);
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
      console.log("나가!");
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
      this.$router.push({
        name: "conference-detail"
      });
    },

    updateMainVideoStreamManager(stream) {
      console.log("updateMainVideoStreamManager===================");
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
     *   1) Initialize a Session in OpenVidu Server   (POST /openvidu/api/sessions)
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

#alertbtn {
  position: relative;
  top: -265px;
  left: 145px;
}
</style>
