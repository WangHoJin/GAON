<template>
  <div>{{ clientData }},{{ idData }}</div>
</template>

<script>
export default {
  name: "ConnetionUser",

  props: {
    subscriber: undefined
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
    idData() {
      const { idData } = this.getConnectionData();
      return idData;
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.subscriber.stream;
      return JSON.parse(connection.data);
    }
  }
};
</script>
