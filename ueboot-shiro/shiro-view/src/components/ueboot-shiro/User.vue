<template>
  <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
</template>

<script>
export default {
  name: 'User',
  data () {
    return {
      formGrid: {
        options: {
          url: {
            page: '/ueboot/user/page',
            save: '/ueboot/user/save',
            delete: '/ueboot/user/delete'
          }
        },
        form: {
          modal: {
            title: 'User'
          },
          columns: [
            {label: 'id', type: 'text', name: 'id', required: true},
            {label: 'orgCode', type: 'text', name: 'orgCode', required: true},
            {label: 'userName', type: 'text', name: 'userName', required: true},
            {label: 'password', type: 'text', name: 'password', required: true},
            {label: 'credentialExpiredDate', type: 'text', name: 'credentialExpiredDate', required: true}

          ]
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            {title: 'id', key: 'id'},
            {title: 'orgCode', key: 'orgCode'},
            {title: 'userName', key: 'userName'},
            {title: 'password', key: 'password'},
            {title: 'credentialExpiredDate', key: 'credentialExpiredDate'}
          ]
        }
      }
    }
  },
  methods: {}
}
</script>
