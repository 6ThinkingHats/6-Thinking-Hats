import axios from "axios";
axios.defaults.withCredentials = true;
export default axios.create({
  baseURL: "http://i7a709.p.ssafy.io:8888",
  headers: {
    "Content-Type": "application/json",
  },
});