
const getHeaders = () => {
  return {
    headers: {
      source: String(localStorage.getItem("source"))
    },
    withCredentials: true
  }
}

const getTokenHeader = () => {
  return {
    withCredentials: true,
  }
}

export { getHeaders, getTokenHeader }
