<template lang="">
  <div id="main-container" class="container">
    <audio id="audioContainer">
      <source id="audioSource" src="" type="audio/mpeg" />
    </audio>
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

    <!-- 출석부 다이얼로그 START -->
    <el-dialog
      width="500px"
      title="출석부"
      v-model="rollbookFormModal"
      center
      top="10vh"
    >
      <div>
        <RollBookCheck
          :publisher="publisher"
          :subscribers="subscribers"
          v-on:save="rollbookFormModal = false"
          v-on:close="rollbookFormModal = false"
        />
      </div>
    </el-dialog>
    <!-- 출석부 다이얼로그  END -->

    <div id="session" v-if="session">
      <el-row>
        <el-col :span="19">
          <!-- 공지배너 START  -->
          <transition name="fade">
            <div class="noticeB" v-if="totalTime > 0">
              <h2>📢 {{ reciveMsg }}</h2>
              <!-- <span>남은시간 [ {{ minutes }}</span> -->
              <div>
                <div style=" display:inline-block">
                  <span>⏰ [ {{ nowtime }} 까지 ] </span>
                </div>
                <div style="color:red; margin-left:5px;display:inline-block">
                  <span> ⏳ [ {{ minutes }}</span>
                  <span>:</span>
                  <span>{{ seconds }} ]</span>
                </div>
              </div>
            </div>
          </transition>
          <!-- 공지배너 END  -->
          <div id="session-header">
            <!-- <h1 id="session-title">{{ mySessionId }}</h1> -->
          </div>
          <div id="main-video">
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
        </el-col>
        <el-col :span="5" style="padding:10px">
          <!-- 채팅/접속자 현황 버튼들 -->
          <div
            style="margin-top:10px; margin-right:10px; height:50px; text-align: right;"
          >
            <!-- 채팅 on/off 버튼 -->
            <!-- 채팅 on 버튼 -->
            <el-button
              type="warning"
              icon="el-icon-chat-dot-round"
              circle
              v-if="!chatting"
              id="buttonChatting"
              @click="chattingOnOff()"
            ></el-button>
            <!-- 채팅 off 버튼 -->
            <el-button
              type="info"
              icon="el-icon-chat-round"
              circle
              v-else
              @click="chattingOnOff()"
            ></el-button>
            <!-- 접속자 현황 on/off 버튼 -->
            <!-- 접속자 현황 on 버튼 -->
            <el-button
              v-if="!connectionUser"
              type="warning"
              icon="el-icon-s-custom"
              circle
              @click="connectionUserOnOff()"
            ></el-button>
            <el-button
              v-else
              type="info"
              icon="el-icon-s-custom"
              circle
              @click="connectionUserOnOff()"
            ></el-button>
          </div>

          <!-- 애니메이션 참고 사이트 https://runebook.dev/ko/docs/vue/guide/transitions-enterleave -->
          <!-- 채팅 창 -->
          <transition name="slide">
            <div
              id="chat-box"
              v-if="chatting"
              style="box-shadow: 5px 5px 5px #99999930;
"
            >
              <MessageList
                :msgs="msgs"
                :myId="publisher.stream.connection.connectionId"
                :fromId="fromId"
              />
              <MessageForm
                style="width:100%"
                @sendMsg="sendMsg"
                :user-name="myUserName"
              />
            </div>
          </transition>

          <!-- 접속자 현황 -->
          <transition name="slide" style="margin:10px 0px;">
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
          </transition>
        </el-col>
      </el-row>
    </div>
  </div>
  <!-- 화상 미팅 관련 버튼들 -->
  <div id="btngroup">
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
  </div>
  <!-- 공지사항 보내기 버튼 -->
  <div id="tools" style="margin-top:10px; margin-left:10px">
    <img
      :src="require(`@/common/img/notice.png`)"
      @click="noticeFormModal = true"
    />

    <!-- 출석체크 버튼 -->
    <img
      style="margin-left: 12px;"
      :src="require(`@/common/img/book.png`)"
      @click="rollbookFormModal = true"
    />
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
import alarm from "@/common/mp3/alarm.mp3";
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

  beforeRouteLeave(to, from, next) {
    console.log(to.fullPath);
    if (to.fullPath.indexOf("board") != -1) {
      console.log("게시판갈거고 bid는1 " + to.params.bid);
      this.bid = to.params.bid;
      // console.log(this.bid);
    } else {
      console.log("게시판안갈거고 id는2" + this.bid);
      // console.log(typeof this.bid);
      this.bid = false; //재할당
    }
    if (to.fullPath.indexOf("rollbook") != -1) {
      console.log("출석부가야함" + to.params.bid);
      if (this.session) {
        this.session.disconnect();
      }
      return next();
    }
    if (to.fullPath == `/conference/${this.$route.params.conferenceId}`) {
      if (this.session) {
        this.session.disconnect();
      }
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

      noticeFormModal: false,
      rollbookFormModal: false,

      fromId: "",
      audioContainer: undefined,
      music: require("../../../common/mp3/alarm.mp3"),

      bid: null
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
    },
    publisher() {
      deep: true;
      // console.log(deep);
      console.log("중복 아디 체크");
      console.log("현재 나");
      console.log(this.publisher);
      if (this.publisher === undefined) {
        this.leaveSession();
      } else {
        console.log(this.publisher.stream.connection.data);
        const { clientData } = JSON.parse(
          this.publisher.stream.connection.data
        );
        const { idData } = JSON.parse(this.publisher.stream.connection.data);
        const nickname1 = clientData;
        const id1 = idData;
        console.log("접속자");
        this.subscribers.forEach(sub => {
          console.log(sub.stream.connection.data);
          // console.log(JSON.parse(sub.stream.connection.data));
          const { clientData } = JSON.parse(sub.stream.connection.data);
          const { idData } = JSON.parse(sub.stream.connection.data);
          console.log(nickname1);
          console.log(clientData);
          if (nickname1 == clientData && id1 == idData) {
            alert("같은 사용자가 존재합니다");
            this.$router.push({
              name: "conference-detail"
            });
            setTimeout("location.reload()", 10);
          }
        });
      }
    }
  },
  methods: {
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
      // console.log(this.bid);
      if (this.bid) {
        // console.log("리브세션에서 실행되는거");
        // console.log(this.bid);
        this.$router.push({
          name: "board",
          params: { bid: this.bid }
        });
        this.bid = "";
      } else {
        console.log("회의 메인으로");
        this.$router.push({
          name: "conference-detail"
        });
      }
    },
    test() {
      var xmlHttpRequest;
      if (window.XMLHttpRequest) {
        // code for Firefox, Mozilla, IE7, etc.
        xmlHttpRequest = new XMLHttpRequest();
      } else if (window.ActiveXObject) {
        // code for IE5, IE6
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
      } else {
        return;
      }

      xmlHttpRequest.open("HEAD", window.location.href.toString(), false);
      xmlHttpRequest.setRequestHeader("ContentType", "text/html");
      xmlHttpRequest.send("");

      var serverDate = xmlHttpRequest.getResponseHeader("Date");
      var date = new Date(serverDate);
      var date2 = new Date();
      console.log("서버시간 : " + date);
      console.log("로컬시간 : " + date2);
    },
    //타이머 시작
    startTimer: function() {
      clearInterval(this.timer);
      var xmlHttpRequest;
      if (window.XMLHttpRequest) {
        // code for Firefox, Mozilla, IE7, etc.
        xmlHttpRequest = new XMLHttpRequest();
      } else if (window.ActiveXObject) {
        // code for IE5, IE6
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
      } else {
        return;
      }

      xmlHttpRequest.open("HEAD", window.location.href.toString(), false);
      xmlHttpRequest.setRequestHeader("ContentType", "text/html");
      xmlHttpRequest.send("");

      var serverDate = xmlHttpRequest.getResponseHeader("Date");
      var date = new Date(serverDate);
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
      var xmlHttpRequest;
      if (window.XMLHttpRequest) {
        // code for Firefox, Mozilla, IE7, etc.
        xmlHttpRequest = new XMLHttpRequest();
      } else if (window.ActiveXObject) {
        // code for IE5, IE6
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
      } else {
        return;
      }

      xmlHttpRequest.open("HEAD", window.location.href.toString(), false);
      xmlHttpRequest.setRequestHeader("ContentType", "text/html");
      xmlHttpRequest.send("");

      var serverDate = xmlHttpRequest.getResponseHeader("Date");
      var nowtime = new Date(serverDate);
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
        var xmlHttpRequest;
        if (window.XMLHttpRequest) {
          // code for Firefox, Mozilla, IE7, etc.
          xmlHttpRequest = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
          // code for IE5, IE6
          xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
          return;
        }

        xmlHttpRequest.open("HEAD", window.location.href.toString(), false);
        xmlHttpRequest.setRequestHeader("ContentType", "text/html");
        xmlHttpRequest.send("");

        var serverDate = xmlHttpRequest.getResponseHeader("Date");
        var date = new Date(serverDate);
        var time = Date.parse(date);
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
    toggleScreanshare(publisher) {
      if (!this.tg) {
        this.tg = true;
        var newPublisher = this.OV.initPublisher(
          "user-video",
          {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: "screen",
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "1920x1080", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false // Whether to mirror your local video or not
          },

          error => {
            // Function to be executed when the method finishes
            if (error) {
              console.error(
                "Error while initializing publisher: =====================================뉴퍼블리셔 게시하는도중에 에러가 발생했다",
                error
              );
              this.tg = false;
              // this.session.unpublish(this.publisher);
              this.publisher = this.tempPublisher;
              this.session.publish(this.publisher);
            } else {
              console.log("Publisher successfully initialized");
            }
          }
        );

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
        this.tg = false;
        this.session.unpublish(this.publisher);
        this.publisher = this.tempPublisher;
        this.session.publish(this.publisher);
      }
    },
    connectionUserOnOff() {
      this.connectionUser = !this.connectionUser;
    },
    //채팅 on/off버튼을 눌렀을 때 실행되는 함수
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
        this.fromId = event.from.connectionId;
        const tmp = this.msgs.slice();
        tmp.push(event.data);
        this.msgs = tmp;
      });
      this.session.on("signal:my-alrarm", event => {
        console.log(event.date + " " + event.from + " " + event.type);
        this.$notify({
          title: "졸지마세요~!!",
          message: h(
            "i",
            { style: "color: teal" },
            "일어나~!~!~!~!~!~!~!~!~!~!~!~!~!!"
          )
        });
        this.playAudio();
      });

      this.session.on("signal:notice", event => {
        var xmlHttpRequest;
        if (window.XMLHttpRequest) {
          // code for Firefox, Mozilla, IE7, etc.
          xmlHttpRequest = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
          // code for IE5, IE6
          xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
          return;
        }

        xmlHttpRequest.open("HEAD", window.location.href.toString(), false);
        xmlHttpRequest.setRequestHeader("ContentType", "text/html");
        xmlHttpRequest.send("");

        var serverDate = xmlHttpRequest.getResponseHeader("Date");
        var date = new Date(serverDate);
        var nowtime = Date.parse(date);
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

    playAudio() {
      const source = document.getElementById("audioSource");
      console.log(source);
      source.src = this.music;
      const audioContainer = document.getElementById("audioContainer");
      console.log("오디오");
      audioContainer.load();
      audioContainer.volume = 0.3;
      console.log(audioContainer);
      const playPromise = audioContainer.play();
      if (playPromise !== undefined) {
        playPromise
          .then(_ => {})
          .catch(error => {
            console.log("에러");
            console.log(error);
            audioContainer.load();
          });
      }
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
  top: -227px;
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
#icon1 {
  width: 40px;
  position: absolute;
  margin: 0px;
  padding: 0px;
  left: 13%;
  top: 10%;
  z-index: 1;
}
#icon2 {
  width: 40px;
  position: absolute;
  margin: 0px;
  padding: 0px;
  left: 13%;
  top: 15%;
  z-index: 1;
}

/* 공지사항 css */
.noticeB {
  word-break: break-all;
  width: 90%;
  /* height: 100px; */
  /* position: fixed; */
  /* top: 0px; */
  /* left: 19%; */
  /* margin-left: 19%; */
  background-color: #ffd344;
  margin-bottom: 5px;
  border-radius: 10px;
  padding: 10px;
  margin-left: 5%;
}

.slide-enter-active {
  animation-duration: 1s;
  animation-name: slidein;
}

.slide-leave-active {
  animation-duration: 1s;
  animation-name: slideout;
}

.fade-enter-active {
  animation-duration: 0.5s;
  animation-name: fadein;
}

.fade-leave-active {
  animation-duration: 0.5s;
  animation-name: fadeout;
}

/* 오른쪽에서 왼쪽으로 슬라이드 등장 */
@keyframes slidein {
  from {
    margin-left: 100%;
    width: 300%;
  }

  to {
    margin-left: 0%;
    width: 100%;
  }
}
/* 왼쪽에서 오른쪽으로 슬라이드 등장 */
@keyframes slideout {
  from {
    margin-left: 0%;
    width: 100%;
  }

  to {
    margin-left: 100%;
    width: 300%;
  }
}

@keyframes fadein {
  from {
    opacity: 0;
  }

  to {
    opacity: 100;
  }
}

@keyframes fadeout {
  from {
    opacity: 100%;
  }

  to {
    opacity: 0%;
  }
}

#btngroup {
  background-color: rgba(110, 110, 110, 0.205);
  margin: 10px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  position: fixed;
  bottom: 0%;
  left: 50%;
  margin: -150px 0px 20px -100px;
  z-index: 1;
}
#tools {
  margin: 10px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  position: absolute;
  top: 0%;
  float: left;
}
/* 선생님용 버튼 */
#tools img {
  background-color: #405038de;
  border-radius: 50%;
  padding: 10px;
  width: 35px;
  height: 35px;
}
#tools img:hover {
  transform: scale(1.2, 1.2);
}
</style>
