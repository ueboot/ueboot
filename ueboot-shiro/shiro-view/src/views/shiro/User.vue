<template>
  <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
  export default {
    name: 'User',
    data() {
      return {
        formGrid: {
          options: {
            version: 'v2',
            url: {
              page: "/platform/user/page",
              save: "/platform/user/save",
              delete: "/platform/user/delete",
            }
          },
          tips: {title: "User", content: "User"},

          form: {
            modal: {
              title: "User"
            },
            columns: [
              {label: "id", type: "text", name: "id", required: true},
              {label: "orgCode", type: "text", name: "orgCode", required: true},
              {label: "username", type: "text", name: "username", required: true},
              {label: "password", type: "text", name: "password", required: true},

            ]
          },
          table: {
            operation: {
              primaryKey: "id",
            },
            columns: [
              {title: 'id', key: 'id'},
              {title: 'orgCode', key: 'orgCode'},
              {title: 'username', key: 'username'},
              {title: 'password', key: 'password'},
            ]
          }
        }
      }
    },
    methods: {}
  }
</script>
