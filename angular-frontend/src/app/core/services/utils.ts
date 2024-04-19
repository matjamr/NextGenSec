
const getHeaders = () => {
  return {
    headers: {
      token: String(localStorage.getItem("token")),
      source: String(localStorage.getItem("source"))
    }
  }
}

const getTokenHeader = () => {
  return {
    headers: {
      token: String(localStorage.getItem("token"))
    }
  }
}

export { getHeaders, getTokenHeader }
