import VueRouter from 'vue-router'
import RegisterPage from '@/views/RegisterPage'
import { createLocalVue, mount } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import registrationService from '@/services/registration-service'

// vm.$router 에 접근할 수 있도록
// 테스트에 Vue Router 추가하기
const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
const router = new VueRouter()

jest.mock('@/services/registration-service')

describe('RegisterPage.vue', async () => {
  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldPassword
  let buttonSubmit
  let registerSpy

  beforeEach(() => {
    wrapper = mount(RegisterPage, {
      localVue,
      router
    })
    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
    registerSpy = jest.spyOn(registrationService, 'register')
  })

  afterEach(() => {
    registerSpy.mockReset()
    registerSpy.mockRestore()
  })

  afterAll(() => {
    jest.restoreAllMocks()
  })

  it('should render correct contents', () => {
    expect(wrapper.find('.logo').attributes().src)
      .toEqual('../assets/logo.png')
    expect(wrapper.find('.tagline').text())
      .toEqual('Open source task management tool')
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldEmailAddress.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text())
      .toEqual('Create account')
  })

  it('should contain data model with initial values', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.emailAddress).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  it('should have form inputs bound with data model', async () => {
    const username = 'sunny'
    const emailAddress = 'sunny@taskagile.com'
    const password = 'JestRocks!'

    await wrapper.setData({
      form: {
        username: username,
        emailAddress: emailAddress,
        password: password
      }
    })
    expect(fieldUsername.element.value).toEqual(username)
    expect(fieldEmailAddress.element.value).toEqual(emailAddress)
    expect(fieldPassword.element.value).toEqual(password)
  })

  it('should have from submit event halder `submitForm`', () => {
    const stub = jest.fn()
    wrapper.setMethods({ submitForm: stub })
    buttonSubmit.trigger('submit')
    expect(stub).toBeCalled()
  })

  /*it('should register when it is a new user', async () => {
    expect.assertions(2)
    const stub = jest.fn()
    wrapper.vm.$router.push = stub
    await wrapper.setData({
      form: {
        username: 'sunny',
        emailAddress: 'sunny@taskagile.com',
        password: 'JestRocks!'
      }
    })
    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(stub).toHaveBeenCalledWith({ name: 'LoginPage' })
  })

  it('should fail it is not a new user', async () => {
    await wrapper.setData({
      form: {
        username: 'ted',
        emailAddress: 'ted@taskagile.com',
        password: 'JestRocks!'
      }
    })
    expect(wrapper.find('.failed').isVisible()).toBe(false)
    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(wrapper.find('.failed').isVisible()).toBe(true)
  })*/

  it('should fail when the email address is invalid', async () => {
    await wrapper.setData({
      form: {
        username: 'test',
        emailAddress: 'bad-email-address',
        password: 'JestRocks!'
      }
    })
  })

  it('should fail when the username is invalid', async () => {
    await wrapper.setData({
      form: {
        username: 'a',
        emailAddress: 'test@taskagile.com',
        password: 'JestRocks!'
      }
    })
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('should fail when the password is invalid', async () => {
    await wrapper.setData({
      form: {
        username: 'test',
        emailAddress: 'test@taskagile.com',
        password: 'bad!'
      }
    })
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })
})
