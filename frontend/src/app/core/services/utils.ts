
const getHeaders = () => {
  return {
    headers: {
      Authorization: "Bearer " + String(localStorage.getItem("token")),
      source: String(localStorage.getItem("source"))
    }
  }
}

const getTokenHeader = () => {
  return {
    headers: {
      Authorization: "Bearer " + String(localStorage.getItem("token"))
    }
  }
}

export { getHeaders, getTokenHeader }
