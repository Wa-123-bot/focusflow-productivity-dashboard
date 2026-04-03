import { useState } from "react";

function TimerPanel({ tasks, refresh }) {
  const [taskId, setTaskId] = useState("");
  const [minutes, setMinutes] = useState(25);

  const formatLocalDateTime = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const mins = String(date.getMinutes()).padStart(2, "0");
    const secs = String(date.getSeconds()).padStart(2, "0");

    return `${year}-${month}-${day}T${hours}:${mins}:${secs}`;
  };

  const saveSession = async () => {
    if (!taskId) return;

    const end = new Date();
    const start = new Date(end.getTime() - minutes * 60000);

    await fetch("http://localhost:8080/api/focus/session", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        taskId,
        startTime: formatLocalDateTime(start),
        endTime: formatLocalDateTime(end),
      }),
    });

    refresh();
  };

  return (
    <div className="card">
      <h2>Focus Session</h2>

      <div className="task-form">
        <select value={taskId} onChange={(e) => setTaskId(e.target.value)}>
          <option value="">Select task</option>
          {tasks.map((task) => (
            <option key={task.id} value={task.id}>
              {task.title}
            </option>
          ))}
        </select>

        <input
          type="number"
          value={minutes}
          onChange={(e) => setMinutes(Number(e.target.value))}
        />

        <button onClick={saveSession} disabled={!taskId}>
          Save Session
        </button>
      </div>
    </div>
  );
}

export default TimerPanel;