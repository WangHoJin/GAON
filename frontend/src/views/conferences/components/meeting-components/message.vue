<template>
  <div>
    {{ msg }}
  </div>
</template>

<script>
export default {
  name: 'Message',
  props: {
    msg: {
      type: String
    }
  }
}
</script>

<style scoped>

</style>