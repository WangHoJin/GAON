<template lang="">
  <div id="main-container" class="container">
    <!-- 공지배너 START  -->
    <transition name="fade" id="transi">
      <div class="noticeB" v-if="totalTime > 0">
        <h2>{{ reciveMsg }}</h2>
        <span>남은시간 [ {{ minutes }}</span>
        <span>:</span>
        <span>{{ seconds }}]</span>
        <span>[{{ nowtime }} 까지 유효 ]</span>
      </div>
    </transition>
    <!-- 공지배너 END  -->

    <!-- 공지보내기 START -->
    <el-dialog
      width="500px"
      title="새 공지사항 보내기"
      v-model="noticeFormModal"
      center
      top="10vh"
    >
      <el-divider></el-divider>
      <!--  공지 dialog일 때 -->
      <el-form>
        <el-form-item
          label="공지사항 내용을 입력해주세요"
          :label-width="formLabelWidth"
          id="notice-make-form-label"
        >
          <el-input
            v-model="noticeMsg"
            autocomplete="off"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item
          label="공지 시간을 설정해주세요(1~3600초)"
          :label-width="formLabelWidth"
          id="notice-make-form-label"
        >
          <el-input v-model.number="sendtime" type="number"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button class="gaon-button" type="warning" @click="makeNotice()"
            >공지사항 생성</el-button
          >
          <el-button
            @click="
              noticeFormModal = false;
              noticeMsg = '';
              sendtime = 0;
            "
            type="info"
            >취소</el-button
          >
        </span>
      </template>
    </el-dialog>
    <!-- 공지보내기 END -->

    <div id="session" v-if="session">
      <el-row>
        <!-- <el-button type="" @click="roll-book-check=true;">출석체크하세요</el-button> -->
        <!-- <RollBookCheck /> -->

        <el-col :span="19">
          <div id="session-header">
            <!-- <h1 id="session-title">{{ mySessionId }}</h1> -->
          </div>
          <div id="main-video" class="col-md-6">
            <user-video
              v-if="mainOnOff"
              :stream-manager="mainStreamManager"
              :mainStream="true"
              v-on:dblclick="deleteMainVideoStreamManager"
            />
          </div>
          <div id="video-container" class="col-md-6">
            <div id="myvideo">
              <user-video
                id="vid"
                :stream-manager="publisher"
                v-on:dblclick="updateMainVideoStreamManager(publisher)"
              />
            </div>

            <!-- 유저 화상회의 목록 출력 START -->
            <div id="userv" v-for="sub in subscribers">
              <user-video
                id="vid"
                :key="sub.stream.connection.connectionId"
                :stream-manager="sub"
                v-on:dblclick="updateMainVideoStreamManager(sub)"
              ></user-video>
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
          <el-button
            v-if="vOnOff"
            type="success"
            icon="el-icon-camera"
            circle
            @click="videoOnOff()"
          ></el-button>
          <el-button
            v-else
            type="danger"
            icon="el-icon-camera-solid"
            circle
            @click="videoOnOff()"
          ></el-button>
          <el-button
            v-if="aOnOff"
            type="success"
            icon="el-icon-microphone"
            circle
            @click="audioOnOff()"
          ></el-button>
          <el-button
            v-else
            type="danger"
            icon="el-icon-turn-off-microphone"
            circle
            @click="audioOnOff()"
          ></el-button>
          <el-button
            type="success"
            icon="el-icon-monitor"
            circle
            @click="toggleScreanshare()"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-close"
            circle
            @click="leaveSession"
          ></el-button>
          <!-- 공지사항 보내기 버튼 -->
          <img
            style="cursor:pointer;"
            :src="require(`@/common/img/notice.png`)"
            @click="noticeFormModal = true"
          />
        </el-col>
        <el-col :span="5">
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
            <MessageForm @sendMsg="sendMsg" :user-name="myUserName" />
          </div>
          <div v-if="connectionUser">
            <div v-if="publisher">
              <el-row>
                <!-- <ConnetionUserList
                  :publisher="publisher"
                  :subscribers="subscribers"
                  @leaveSession="leaveSession"
                /> -->
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
    <el-row>
      <div id="conferenceFooter"></div>
    </el-row>
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

const OPENVIDU_SERVER_URL = "https://i5b101.p.ssafy.io:443";
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
  beforeRouteLeave(to, from, next) {
    // console.log(to.fullPath);
    if (to.fullPath == `/conference/${this.$route.params.conferenceId}`) {
      return next();
    } else {
      if (this.session) {
        this.session.disconnect();
      }
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
      return next();
    }
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
      mainOnOff: false,
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      myUserId: "",
      tg: false,
      width: "640",
      height: "400",

      noticeSig: "",
      reciveMsg: "",
      noticeMsg: "",
      sendtime: 0,
      timer: null,
      totalTime: 0,
      resetButton: false,

      noticeFormModal: false
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
    console.log("세션 검색");
    console.log(this.getSession(this.mySessionId));
    this.joinSession();
  },
  computed: {
    // subscribers() {
    //   console.log("감지 발생");
    //   this.subscribers.forEach(sub => {
    //     if (sub.stream.typeOfVideo == "SCREEN") {
    //       console.log("내가 공유한 놈이다");
    //       this.updateMainVideoStreamManager(sub);
    //     }
    //   });
    //   return this.subscribers;
    // }
    minutes: function() {
      const minutes = Math.floor(this.totalTime / 60);
      return this.padTime(minutes);
    },
    seconds: function() {
      const seconds = this.totalTime - this.minutes * 60;
      return this.padTime(seconds);
    }
  },
  watch: {
    sendtime: function() {
      if (this.sendtime >= 3600) {
        console.log("이상 이상");
        this.sendtime = 3600;
      }
    }
  },
  methods: {
    //타이머종료시간 설정
    setTimertime() {},
    //타이머 시작
    startTimer: function() {
      clearInterval(this.timer);
      var date = new Date();
      console.log(date);
      this.nowtime =
        date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
      this.timer = setInterval(() => this.countdown(), 1000);
      this.resetButton = true;
    },
    stopTimer: function() {
      clearInterval(this.timer);
      this.timer = nullthis.resetButton = true;
    },
    resetTimer: function() {
      this.totalTime = 1 * 60;
      clearInterval(this.timer);
      this.timer = null;
      this.resetButton = false;
    },
    padTime: function(time) {
      return (time < 10 ? "0" : "") + time;
    },
    countdown: function() {
      if (this.totalTime >= 1) {
        this.totalTime--;
      } else {
        this.totalTime = 0;
        this.resetTimer;
      }
    },
    //타이머 종료
    sendPublisher(subId) {
      // Sender of the message (after 'session.connect')
      this.session
        .signal({
          data: subId, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "my-subId" // The type of message (optional)
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch(error => {
          console.error(error);
        });
    },
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
    makeNotice() {
      this.noticeFormModal = false;
      var nowtime = new Date();
      var nexttime = Date.parse(nowtime) + this.sendtime * 1000;
      var sig = JSON.stringify({
        nexttime: nexttime,
        noticeMsg: this.noticeMsg
      });
      console.log(sig);
      this.noticeSig = sig;
      this.sendNotice();
    },
    checkNotice() {
      if (this.noticeSig != "") {
        var time = Date.parse(new Date());
        var signal = JSON.parse(this.noticeSig);
        if (time < signal.nexttime) {
          this.sendNotice();
        } else {
          this.noticeSig = "";
          this.noticeMsg = "";
        }
      }
    },
    sendNotice() {
      this.session
        .signal({
          data: this.noticeSig, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "notice" // The type of message (optional)
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
    toggleScreanshare(publisher) {
      if (!this.tg) {
        this.tg = true;
        var newPublisher = this.OV.initPublisher("user-video", {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: "screen",
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: "1920x1080", // The resolution of your video
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
              this.mainOnOff = false;
            });
        });
        this.width = "1920";
        this.height = "1080";
        // this.updateMainVideoStreamManager(newPublisher);
        newPublisher.once("accessAllowed", () => {
          try {
            newPublisher.stream
              .getMediaStream()
              .getVideoTracks()[0]
              .applyConstraints({
                width: this.width,
                height: this.height
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
        // this.updateMainVideoStreamManager(this.publisher);
      } else {
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
      console.log(" 공유 여부 ");
      console.log(this.subscribers[1].stream.typeOfVideo);
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
        console.log("인원 변경이 감지되었다.");
        this.checkNotice();
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
        // console.log(event.data); // Message
        // console.log(event.from); // Connection object of the sender
        // console.log(event.type); // The type of message ("my-chat")
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

      this.session.on("signal:notice", event => {
        var nowtime = Date.parse(new Date());
        var data = JSON.parse(event.data);
        var nexttime = data.nexttime;
        var msg = data.noticeMsg;
        var endtime = (nexttime - nowtime) / 1000;
        console.log(endtime + " " + msg);
        this.reciveMsg = msg;
        this.totalTime = endtime;
        this.startTimer();
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
              resolution: this.width + "x" + this.height, // "320x200", // The resolution of your video
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
      this.mainOnOff = true;
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
      this.mainStreamManager.stream.videoDimensions = {
        width: 960,
        height: 600
      };
      console.log("바뀐 메인스트림정보");
      console.log(this.mainStreamManager);
      console.log(this.mainStreamManager.stream.videoDimensions);
    },
    deleteMainVideoStreamManager() {
      this.mainOnOff = false;
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
    getSession(mySessionId) {
      axios
        .get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${mySessionId}`)
        .then(response => {
          console.log("가져온 세션");
          console.log(response);
          console.log(response.data);
        })
        .catch(err => {
          console.log("못가져옴");
          console.log(err);
        });
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
#alertbtn {
  display: inline;
  position: relative;
  top: -260px;
  float: right;
}

#userv {
  display: inline-block;
  margin: 1px;
}
#myvideo {
  display: inline-block;
  margin: 1px;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
.noticeB {
  background-color: #ffd344;
  margin: 20px;
  border-radius: 20px;
  padding: 10px;
}
</style>
