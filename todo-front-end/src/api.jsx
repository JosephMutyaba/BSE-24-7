import axios from 'axios';

// const API_URL = 'http://todolistapp-env.eba-eqgzcdp5.us-east-1.elasticbeanstalk.com/api/todos';
const API_URL = import.meta.env.VITE_API_URL;

export const getTodos = () => axios.get(API_URL);
export const createTodo = (todo) => axios.post(API_URL, todo);
export const updateTodo = (id, todo) => axios.put(`${API_URL}/${id}`, todo);
export const deleteTodo = (id) => axios.delete(`${API_URL}/${id}`);
