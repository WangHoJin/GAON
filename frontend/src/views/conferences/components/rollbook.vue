<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <el-calendar>
        <template #dateCell="{data}">
          <div id="calendar-day" :class="data.isSelected ? 'is-selected' : ''" @click="selectday(data.day)">
            {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : '' }}
          </div>
        </template>
      </el-calendar>

        <el-table
        :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">
        <el-table-column
          label="이름"
          prop="name">
        </el-table-column>
        <el-table-column
          align="right"
          >
          <template #header>
            <el-input
              v-model="search"
              size="mini"
              placeholder="Type to search"/>
          </template>
          <template #default="scope">
            <el-radio v-model="tableData[scope.$index].attendance" label="1">출석</el-radio>
            <el-radio v-model="tableData[scope.$index].attendance" label="0">결석</el-radio>
          </template>
        </el-table-column>
      </el-table>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
  export default {
    data() {
      return {
        tableData: [{
          date: '2016-05-03',
          name: 'Tom',
          attendance: '1'
        }, {
          date: '2016-05-02',
          name: 'John',
          attendance: '0'
        }, {
          date: '2016-05-04',
          name: 'Morgan',
          attendance: '1'
        }, {
          date: '2016-05-01',
          name: 'Jessy',
          attendance: '1'
        }],
        search: '',
        value: new Date()
      }
    },

    methods: {
      selectday(day) {
        console.log(day)
      }
    },
  }
</script>
<style>
  .is-selected {
    color: #1989FA;
  }

  #calendar-day {
    width: 100%;
    height: 100%;
    vertical-align: middle;
  }
</style>
