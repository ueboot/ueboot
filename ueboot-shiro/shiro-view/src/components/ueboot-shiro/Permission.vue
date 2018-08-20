<template>
    <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
export default {
  name: 'Permission',
  data () {
    return {
      formGrid: {
        options: {
          version: 'v2',
          url: {
            page: '/ueboot/permission/page',
            save: '/ueboot/permission/save',
            delete: '/ueboot/permission/delete'
          }
        },
        tips: {title: 'Permission', content: 'Permission'},

        form: {
          modal: {
            title: 'Permission'
          },
          columns: [
            {label: 'id', type: 'text', name: 'id', required: true}

          ]
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            { title: 'id', key: 'id' }
          ]
        }
      }
    }
  },
  methods: {

  }
}
</script>
