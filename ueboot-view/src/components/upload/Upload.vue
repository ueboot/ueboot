<template>
  <div class="ue-upload-file">
    <div class="vuf-drop-area"
         @click="handleClick"
         @dragleave="handleDragLeave"
         @dragover="handleDragOver"
         @dragenter="handleDragEnter"
         @drop="handleDrop">
      <i class="vuf-icon1" v-show="loading != 1">
        <i class="vuf-icon1-arrow"></i>
        <i class="vuf-icon1-body"></i>
        <i class="vuf-icon1-bottom"></i>
      </i>
      <span class="vuf-hint" v-show="loading !== 1">{{ lang.hint }}</span>
      <span class="vuf-loading" v-show="loading === 1">{{ lang.loading }}</span>
      <div class="vuf-progress-wrap" v-show="loading === 1">
        <span class="vuf-progress" :style="progressStyle"></span>
      </div>
      <span class="vuf-no-supported-hint" v-show="!isSupported">{{ lang.noSupported }}</span>
      <input type="file" v-show="false" @change="handleChange" ref="fileinput">
    </div>
    <div class="vuf-error" v-show="hasError">
      <i class="vuf-icon2"></i>
      {{ errorMsg }}
    </div>
    <div class="vuf-success" v-show="loading === 2">
      <i class="vuf-icon3"></i>
      {{ lang.success }}
    </div>
  </div>
</template>

<script>
import Log from '../../utils/Log'
export default {
  name: 'ueUpload',
  props: {
    // 域，上传文件name，触发事件会带上（如果一个页面多个图片上传控件，可以做区分
    scope: {
      type: String,
      'default': 'Upload.vue'
    },
    // 显示该控件与否
    isShow: {
      'default': true
    },
    // 上传服务地址
    action: {
      type: String,
      'default': ''
    },
    // 其他要上传文件附带的数据，对象格式
    params: {
      type: Object,
      'default': {}
    },
    // 单文件大小限制 单位kb
    maxSize: {
      type: Number,
      'default': 2048
    },
    // 上传文件类型 数据类型字符串 如：|xls|xlsx|sheet|vnd.openxmlformats-officedocument.spreadsheetml.sheet|
    fileType: {
      'default': null
    },
    // 是否多文件上传，默认 false 单文件上传
    multiFile: {
      'default': false
    }
  },
  data () {
    let isSupported = true

    let lang = {
      hint: '点击，或将文件拖动至此处',
      loading: '正在上传……',
      noSupported: '浏览器不支持该功能，请使用IE10以上或其他现代浏览器！',
      success: '上传成功',
      fail: '上传失败',
      error: {
        multiFile: '仅限单文件上传',
        outOfSize: '单文件大小超限 '
      }
    }
    if (typeof FormData !== 'function') {
      isSupported = false
    }
    return {
      loading: 0, // 0未开始 1上传中 2成功 3错误
      lang,
      isSupported,
      hasError: false,
      files: '',
      progress: 0,
      errorMsg: ''
    }
  },
  computed: {
    progressStyle () {
      let { progress } = this
      return {
        width: progress + '%'
      }
    }
  },
  watch: {
    'isShow': function (newValue) {
      if (newValue) {
        this.reset()
      }
    }
  },
  methods: {
    handleDragLeave (e) {
      e.preventDefault()
    },
    handleDrop (e) {
      e.preventDefault()
      if (this.loading !== 1) {
        let files = e.dataTransfer.files
        this.reset()
        if (this.checkFiles(files)) {
          this.upload2(files)
        }
      }
    },
    handleDragEnter (e) {
      e.preventDefault()
    },
    handleDragOver (e) {
      e.preventDefault()
    },
    handleClick (e) {
      if (this.loading !== 1) {
        if (e.target !== this.$refs.fileinput) {
          e.preventDefault()
          this.$refs.fileinput.click()
        }
      }
    },
    handleChange (e) {
      if (this.loading !== 1) {
        let files = e.target.files
        this.reset()
        if (this.checkFiles(files)) {
          this.upload2(files)
        }
      }
    },
    checkFiles (files) {
      let that = this

      let { lang, maxSize, fileType, multiFile } = that

      let fileNum = files.length
      // 是否文件为空
      if (fileNum < 1) {
        this.message.info({
          content: '温馨提醒 ' + lang.error.multiFile
        })
        return false
      }
      if (!fileType) {
        return true
      }
      let fileType_ = ('|' + fileType.join('|') + '|').replace('xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
      // 仅限单文件？
      if (!multiFile) {
        if (fileNum > 1) {
          this.$Notice.error({
            title: '文件数量超限',
            desc: lang.error.multiFile
          })
          return false
        }
        // 仅限支持配置文件类型范围类文件
        let file = files[0]
        let _fileType = file.type
        if (!_fileType) {
          _fileType = file.name.substr(file.name.indexOf('.') + 1)
        }
        Log.d(_fileType)
        let type = '|' + _fileType + '|'
        if (fileType_ && fileType_.indexOf(type) < 0) {
          let message = '文件类型错误仅限支持' + fileType.join(',') + '格式文件'
          that.$Message.error({
            content: message,
            duration: 10,
            closable: true
          })
          return false
        }

        // 超出大小
        let maxSizeM = file.size / 1024
        if (maxSizeM > maxSize) {
          this.$Notice.error({
            title: '文件超限',
            desc: lang.error.outOfSize + '[实际：' + Math.round(maxSizeM, 2) + 'KB, 限制：' + maxSize + 'KB]'
          })
          return false
        }
      } else {
        for (let i = 0; i < fileNum; i++) {
          // 仅限支持文件类型
          let file = files[0]
          let _fileType = file.type
          if (!_fileType) {
            _fileType = file.name.substr(file.name.indexOf('.') + 1)
          }
          let type = '|' + _fileType + '|'
          if (fileType_ && fileType_.indexOf(type) < 0) {
            this.$Notice.error({
              title: '文件类型错误',
              desc: '仅限支持' + fileType.join(',') + '格式文件'
            })
            return false
          }
          // 超出大小
          let maxSizeM = file.size / 1024
          if (maxSizeM > maxSize) {
            this.$Notice.error({
              title: '文件超限',
              desc: lang.error.outOfSize + '[实际：' + Math.round(maxSizeM, 2) + 'KB, 限制：' + maxSize + 'KB]'
            })
            return false
          }
        }
      }
      return true
    },
    reset () {
      let that = this
      that.loading = 0
      that.hasError = false
      that.errorMsg = ''
      that.progress = 0
    },
    upload (files) {
      let that = this

      let { action, params, multiFile, scope } = this

      let fmData = new FormData()
      // 判断是否单文件
      if (!multiFile) {
        fmData.append(scope, files[0])
      } else {
        fmData.append(scope, files)
      }
      // 添加其他参数
      if (typeof params === 'object' && params) {
        Object.keys(params).forEach((k) => {
          fmData.append(k, params[k])
        })
      }
      // 监听进度回调
      const uploadProgress = function (event) {
        if (event.lengthComputable) {
          that.progress = 100 * Math.round(event.loaded) / event.total
        }
      }
      // 上传文件
      that.loading = 1
      new Promise(function (resolve, reject) {
        let client = new XMLHttpRequest()
        client.open('POST', action, true)
        client.onreadystatechange = function () {
          if (this.readyState !== 4) {
            return
          }
          if (this.status === 200) {
            resolve(this.responseText)
          } else {
            reject(this.responseText)
          }
        }
        client.upload.addEventListener('progress', uploadProgress, false) // 监听进度
        client.send(fmData)
      }).then(
        // 上传成功
        function (resData) {
          that.files = ''
          that.loading = 0
          that.$Notice.success({
            title: '上传结果',
            desc: '文件已上传成功'
          })
          that.$Message.success({
            content: '上传结果',
            closable: true
          })
        },
        // 上传失败
        function (resData) {
          Log.d(resData)
          that.loading = 0
          that.files = ''
          let message = '文件上传失败'
          if (resData && resData['message']) {
            message = resData['message']
          }
          that.$Message.error({
            content: message,
            duration: 10,
            closable: true
          })
        }
      )
    }
  }
}
</script>

<style scoped>
    @import "./css/upload.css";
</style>
