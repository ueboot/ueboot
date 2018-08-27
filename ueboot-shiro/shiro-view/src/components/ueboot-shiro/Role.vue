<template>
  <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
</template>

<script>
export default {
  name: 'Role',
  data () {
    return {
      formGrid: {
        options: {
          url: {
            page: '/ueboot/role/page',
            save: '/ueboot/role/save',
            delete: '/ueboot/role/delete'
          }
        },
        tips: {title: 'Role', content: 'Role'},

        form: {
          modal: {
            title: 'Role'
          },
          columns: [
            {label: 'id', type: 'text', name: 'id', required: true},
            {label: 'code', type: 'text', name: 'code', required: true},
            {label: 'name', type: 'text', name: 'name', required: true},
            {label: 'description', type: 'text', name: 'description', required: true},
            {label: 'available', type: 'text', name: 'available', required: true}

          ]
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            {title: 'id', key: 'id'},
            {title: 'code', key: 'code'},
            {title: 'name', key: 'name'},
            {title: 'description', key: 'description'},
            {title: 'available', key: 'available'}
          ]
        }
      }
    }
  },
  methods: {}
}
</script>
