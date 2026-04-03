import { useEffect, useState } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import TaskPanel from "./components/TaskPanel";
import TimerPanel from "./components/TimerPanel";

function App() {
  const [tasks, setTasks] = useState([]);
  const [stats, setStats] = useState(null);
  const [weekly, setWeekly] = useState([]);

  const loadData = async () => {
    const [tasksRes, statsRes, weeklyRes] = await Promise.all([
      fetch("http://localhost:8080/api/tasks"),
      fetch("http://localhost:8080/api/analytics/dashboard"),
      fetch("http://localhost:8080/api/analytics/weekly"),
    ]);

    setTasks(await tasksRes.json());
    setStats(await statsRes.json());
    setWeekly(await weeklyRes.json());
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
    <div className="app">
      <h1>FocusFlow Dashboard</h1>
      <Dashboard stats={stats} weekly={weekly} />
      <TaskPanel tasks={tasks} refresh={loadData} />
      <TimerPanel tasks={tasks} refresh={loadData} />
    </div>
  );
}

export default App;