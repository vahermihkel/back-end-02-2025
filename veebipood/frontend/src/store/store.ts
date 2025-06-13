import { configureStore } from '@reduxjs/toolkit'
import counterReducer from './CartSumRedux'

export default configureStore({
  reducer: {
    counter: counterReducer
  }
})